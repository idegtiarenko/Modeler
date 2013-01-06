package com.gman.modeler.gui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.gman.modeler.api.Modeler;
import com.gman.modeler.api.Queue;
import com.gman.modeler.api.Service;
import com.gman.modeler.gui.ComponentsFactory;
import com.gman.modeler.gui.api.DataModifier;

/**
 * @author gman
 */
public class QSchemeEditorComponent extends DataModifier {

    private Modeler modeler;

    private final ComponentsFactory factory;
    private final JButton saveButton;
    private final JToggleButton addButton;
    private final JPopupMenu controlMenu;
    private final ServiceButtonListener listener = new ServiceButtonListener();

    private ServiceButton selected = null;
    private final List<ServiceButton> elements = new ArrayList<>();
    private final List<QueueLine> lines = new ArrayList<>();

    private static final int BUTTON_WIDTH = 100;
    private static final int BUTTON_HEIGHT = 25;
    private static final int RADIUS = 10;

    /**
     * Creates new form QSchemeEditorComponent
     */
    public QSchemeEditorComponent() {
        setLayout(null);
        factory = new ComponentsFactory(null);
        saveButton = new JButton("save");
        saveButton.setBounds(0, 0, BUTTON_WIDTH, BUTTON_HEIGHT);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (checkIncomeOutcome()) {
                    getMediator().updateModeler(modeler);
                } else {
                    showErrorMessage("There are one or more invalid services");
                }
            }
        });
        addButton = new JToggleButton("add");
        addButton.setBounds(BUTTON_WIDTH, 0, BUTTON_WIDTH, BUTTON_HEIGHT);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (addButton.isSelected()) {
                    addServiceToModeler(e.getX(), e.getY());
                    addButton.setSelected(false);
                    QSchemeEditorComponent.this.repaint();
                }
            }
        });

        final JMenuItem updateService = new JMenuItem("Update service");
        updateService.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateServiceInModel();
                selected = null;
                QSchemeEditorComponent.this.repaint();
            }
        });

        final JMenuItem removeIncome = new JMenuItem("Remove income");
        removeIncome.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                removeIncomeFromModel();
                selected = null;
                QSchemeEditorComponent.this.repaint();
            }
        });
        final JMenuItem removeOutcome = new JMenuItem("Remove outcome");
        removeOutcome.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                removeOutcomeFromModel();
                selected = null;
                QSchemeEditorComponent.this.repaint();
            }
        });
        final JMenuItem removeService = new JMenuItem("Remove service");
        removeService.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                removeServiceFromModel();
                selected = null;
                QSchemeEditorComponent.this.repaint();
            }
        });

        controlMenu = new JPopupMenu();
        controlMenu.add(updateService);
        controlMenu.add(new javax.swing.JPopupMenu.Separator());
        controlMenu.add(removeIncome);
        controlMenu.add(removeOutcome);
        controlMenu.add(new javax.swing.JPopupMenu.Separator());
        controlMenu.add(removeService);

        reallocateControls();
    }

    @Override
    public String getComponentName() {
        return "Q-scheme";
    }

    @Override
    public void setModeler(Modeler modeler) {
        this.modeler = modeler;
        reallocateModel();
    }

    //model methods

    private boolean checkIncomeOutcome() {
        for (final ServiceButton sb : elements) {
            if (sb.getClass().equals(ServiceButton.class) && (
                    sb.getIncome() == null || sb.getOutcome().isEmpty())) {
                return false;
            }
        }
        return true;
    }

    private void addServiceToModeler(int x, int y) {
        final Service newService = factory.showDialogFor(Service.class, getMediator());
        if (newService != null) {
            modeler.getServices().add(newService);
            final ServiceButton sb = new ServiceButton(newService, x - BUTTON_WIDTH / 2, y - BUTTON_HEIGHT / 2);
            elements.add(sb);
            add(sb);
        }
    }

    private void addQueueToServices(QueueLine line) {
        if (line.getEnd().getClass().equals(IncomeServiceButton.class)) {
            showErrorMessage("You can not set income of the income");
            return;
        }
        if (line.getStart().getClass().equals(OutcomeServiceButton.class)) {
            showErrorMessage("You can not set outcome of the outcome");
            return;
        }
        if (lines.contains(line)) {
            showErrorMessage("This queue already exists");
            return;
        }

        final Queue q;
        if (line.getEnd().getIncome() != null) {
            q = line.getEnd().getIncome();
        } else {
            q = factory.showDialogFor(Queue.class, getMediator());
            if (q == null) {
                showErrorMessage("Queue has not been created");
                return;
            }
        }

        if (line.getStart().getClass().equals(ServiceButton.class)) {
            line.getStart().getOutcome().add(q);
        } else {
            modeler.getIncome().add(q);
        }
        if (line.getEnd().getClass().equals(ServiceButton.class) && line.getEnd().getIncome() == null) {
            line.getEnd().setIncome(q);
        }
        lines.add(line);
    }

    private void updateServiceInModel() {
        final Service newService = factory.showDialogFor(Service.class, getMediator(), selected.getService());
        if (newService != null) {
            newService.setIncome(selected.getService().getIncome());
            newService.getOutcome().addAll(selected.getService().getOutcome());
            final ServiceButton sb = new ServiceButton(newService, selected.getX(), selected.getY());
            modeler.getServices().add(newService);
            elements.add(sb);
            add(sb);
            for (final QueueLine line : lines) {
                if (line.getEnd().equals(selected)) {
                    line.setEnd(sb);
                } else if (line.getStart().equals(selected)) {
                    line.setStart(sb);
                }
            }
            modeler.getServices().remove(selected.getService());
            elements.remove(selected);
            remove(selected);
        }
    }

    private void removeServiceFromModel() {
        removeIncomeFromModel();
        removeOutcomeFromModel();
        elements.remove(selected);
        remove(selected);
        modeler.getServices().remove(selected.getService());
    }

    private void removeIncomeFromModel() {
        final Queue toRemove = selected.getIncome();
        selected.setIncome(null);
        for (final ServiceButton sb : elements) {
            if (sb.getOutcome().contains(toRemove)) {
                sb.getOutcome().remove(toRemove);
                lines.remove(new QueueLine(sb, selected));
            }
        }
    }

    private void removeOutcomeFromModel() {
        final List<Queue> toRemove = new ArrayList<>(selected.getOutcome());
        selected.getOutcome().clear();
        for (final ServiceButton sb : elements) {
            if (toRemove.contains(sb.getIncome())) {
                final Queue incomeToRemove = sb.getIncome();
                boolean isUsed = false;
                for (ServiceButton isb : elements) {
                    if (sb != isb && isb.getOutcome().contains(incomeToRemove)) {
                        isUsed = true;
                    }
                }
                if (!isUsed) {
                    sb.setIncome(null);
                }
                lines.remove(new QueueLine(selected, sb));
            }
        }
    }

    //GUI methods

    private void showErrorMessage(String text) {
        JOptionPane.showMessageDialog(this, text, "error", JOptionPane.ERROR_MESSAGE);
    }

    private void reallocateModel() {
        removeAll();
        reallocateControls();
        //income
        final ServiceButton income = new IncomeServiceButton(0, (getHeight() - BUTTON_HEIGHT) / 2);

        //services
        final Set<Queue> reachable = new HashSet<>(modeler.getIncome());
        final Set<Queue> reachableNext = new HashSet<>();
        final List<Service> services = new ArrayList<>(modeler.getServices());
        elements.clear();
        elements.add(income);

        int x = 0;
        int y;
        while (!services.isEmpty()) {
            x += BUTTON_WIDTH * 1.5;
            y = 0;
            final Iterator<Service> sIterator = services.iterator();
            while (sIterator.hasNext()) {
                final Service s = sIterator.next();
                if (reachable.contains(s.getIncome())) {
                    reachableNext.addAll(s.getOutcome());
                    sIterator.remove();
                    y += BUTTON_HEIGHT * 1.5;
                    final ServiceButton sb = new ServiceButton(s, x, y);
                    elements.add(sb);
                }
            }
            reachable.addAll(reachableNext);
            reachableNext.clear();
        }
        //outcome
        final ServiceButton outcome = new OutcomeServiceButton(getWidth() - BUTTON_WIDTH, (getHeight() - BUTTON_HEIGHT) / 2);
        elements.add(outcome);
        //queues
        lines.clear();
        for (ServiceButton incomer : elements) {
            add(incomer);
            for (ServiceButton outcomer : elements) {
                if (incomer.getOutcome().contains(outcomer.getIncome())) {
                    lines.add(new QueueLine(incomer, outcomer));
                }
            }
        }
        repaint();
    }

    private void reallocateControls() {
        add(saveButton);
        add(addButton);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        for (QueueLine line : lines) {
            g.drawLine(line.getStart().getX() + BUTTON_WIDTH,
                    line.getStart().getY() + BUTTON_HEIGHT / 2,
                    line.getEnd().getX(),
                    line.getEnd().getY() + BUTTON_HEIGHT / 2);
            g.fillOval(line.getEnd().getX() - RADIUS / 2,
                    line.getEnd().getY() + BUTTON_HEIGHT / 2 - RADIUS / 2,
                    RADIUS, RADIUS);
        }
    }

    private class ServiceButton extends JButton {

        private final Service service;

        public ServiceButton(Service service, int x, int y) {
            super(service != null ? service.getName() : "");
            this.service = service;
            addMouseListener(listener);
            addMouseMotionListener(listener);
            setLocation(x, y);
            setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        }

        protected Queue getIncome() {
            return service.getIncome();
        }

        protected void setIncome(Queue queue) {
            service.setIncome(queue);
        }

        protected List<Queue> getOutcome() {
            return service.getOutcome();
        }

        protected Service getService() {
            return service;
        }
    }

    private class IncomeServiceButton extends ServiceButton {

        public IncomeServiceButton(int x, int y) {
            super(null, x, y);
            setText("Income");
        }

        @Override
        protected Queue getIncome() {
            return null;
        }

        @Override
        protected void setIncome(Queue queue) {

        }

        @Override
        protected List<Queue> getOutcome() {
            return modeler.getIncome();
        }
    }

    private class OutcomeServiceButton extends ServiceButton {

        public OutcomeServiceButton(int x, int y) {
            super(null, x, y);
            setText("Outcome");
        }

        @Override
        protected Queue getIncome() {
            return modeler.getOutcome();
        }

        @Override
        protected void setIncome(Queue queue) {
        }

        @Override
        protected List<Queue> getOutcome() {
            return Collections.<Queue>emptyList();
        }
    }

    private class QueueLine {

        private ServiceButton start;
        private ServiceButton end;

        public QueueLine(ServiceButton start, ServiceButton end) {
            this.start = start;
            this.end = end;
        }

        public ServiceButton getStart() {
            return start;
        }

        public void setStart(ServiceButton start) {
            this.start = start;
        }

        public ServiceButton getEnd() {
            return end;
        }

        public void setEnd(ServiceButton end) {
            this.end = end;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final QueueLine other = (QueueLine) obj;
            return Objects.equals(this.start, other.start)
                    && Objects.equals(this.end, other.end);
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 71 * hash + Objects.hashCode(this.start);
            hash = 71 * hash + Objects.hashCode(this.end);
            return hash;
        }
    }

    private class ServiceButtonListener extends MouseAdapter {

        private int x, y, ix, iy;

        @Override
        public void mousePressed(MouseEvent e) {
            x = e.getX();
            y = e.getY();
            ix = ((ServiceButton) e.getSource()).getX();
            iy = ((ServiceButton) e.getSource()).getY();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            final ServiceButton s = (ServiceButton) e.getSource();
            s.setLocation(s.getX() - x + e.getX(),
                    s.getY() - y + e.getY());
            QSchemeEditorComponent.this.repaint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            final ServiceButton source = (ServiceButton) e.getSource();
            final Point location = source.getLocation();
            final Container container = source.getParent();
            for (int i = 0; i < container.getComponentCount(); i++) {
                if (container.getComponent(i) != e.getSource() &&
                        collides(container.getComponent(i).getLocation(), location)) {
                    source.setLocation(ix, iy);
                    final ServiceButton collider = (ServiceButton) container.getComponent(i);
                    final QueueLine line = new QueueLine(source, collider);
                    addQueueToServices(line);
                    break;
                }
            }
            QSchemeEditorComponent.this.repaint();

        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON3) {
                selected = (ServiceButton) e.getSource();
                controlMenu.show((ServiceButton) e.getSource(), e.getX(), e.getY());
            }
        }


        private boolean collides(Point p1, Point p2) {
            return Math.abs(p1.getX() - p2.getX()) < BUTTON_WIDTH &&
                    Math.abs(p1.getY() - p2.getY()) < BUTTON_HEIGHT;
        }
    }

    /**
     * This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(null);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
