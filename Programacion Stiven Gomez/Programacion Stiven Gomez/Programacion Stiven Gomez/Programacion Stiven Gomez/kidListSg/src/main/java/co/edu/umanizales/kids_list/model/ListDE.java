package co.edu.umanizales.kids_list.model;

import lombok.Data;

@Data
public class ListDE {
    private NodeDE head;
    private NodeDE tail;
    private int size;

    // Agregar al final de la lista
    public void add(Kid kid) {
        NodeDE newNode = new NodeDE(kid);
        if (head == null) {
            // Si la lista está vacía, el nuevo nodo será tanto la cabeza como la cola.
            head = newNode;
            tail = newNode;
        } else {
            // El nuevo nodo será el siguiente de la cola actual.
            tail.setNext(newNode);
            // El nuevo nodo tendrá como anterior a la cola.
            newNode.setPrev(tail);
            // Ahora el nuevo nodo se convierte en la cola.
            tail = newNode;
            // Asegúrate de que la cola no apunte cíclicamente a sí misma.
            tail.setNext(null);
        }
        size++;
    }

    // Agregar al inicio de la lista
    public void addToStart(Kid kid) {
        NodeDE newNode = new NodeDE(kid);
        if (head == null) {
            // Si la lista está vacía, el nuevo nodo será tanto la cabeza como la cola.
            head = newNode;
            tail = newNode;
        } else {
            // El nuevo nodo apunta al nodo que antes era la cabeza.
            newNode.setNext(head);
            // El nodo que era la cabeza ahora tiene un puntero al nuevo nodo como su anterior.
            head.setPrev(newNode);
            // El nuevo nodo se convierte en la nueva cabeza.
            head = newNode;
            // Asegúrate de que la cabeza no apunte cíclicamente a sí misma.
            head.setPrev(null);
        }
        size++;
    }

    // Agregar en una posición específica
    public void addAtPosition(Kid kid, int position) {
        if (position < 0 || position > size) {
            throw new IndexOutOfBoundsException("Posición inválida");
        }

        if (position == 0) {
            addToStart(kid);
        } else if (position == size) {
            add(kid);
        } else {
            NodeDE newNode = new NodeDE(kid);
            NodeDE temp = head;
            for (int i = 0; i < position - 1; i++) {
                temp = temp.getNext();
            }
            newNode.setNext(temp.getNext());
            newNode.setPrev(temp);
            if (temp.getNext() != null) {
                temp.getNext().setPrev(newNode);
            }
            temp.setNext(newNode);
            size++;
        }
    }

    // Eliminar en una posición específica
    public void removeAtPosition(int position) {
        if (position < 0 || position >= size) {
            throw new IndexOutOfBoundsException("Posición inválida");
        }

        if (position == 0) {
            removeFirst();
        } else if (position == size - 1) {
            removeLast();
        } else {
            NodeDE temp = head;
            for (int i = 0; i < position; i++) {
                temp = temp.getNext();
            }
            temp.getPrev().setNext(temp.getNext());
            temp.getNext().setPrev(temp.getPrev());
            size--;
        }
    }

    // Eliminar el primer nodo
    private void removeFirst() {
        if (head != null) {
            if (head == tail) {
                head = null;
                tail = null;
            } else {
                head = head.getNext();
                head.setPrev(null);
            }
            size--;
        }
    }

    // Eliminar el último nodo
    private void removeLast() {
        if (tail != null) {
            if (tail == head) {
                head = null;
                tail = null;
            } else {
                tail = tail.getPrev();
                tail.setNext(null);
            }
            size--;
        }
    }

    // Eliminar por ID
    public void removeById(int id) {
        if (head == null) return;

        if (head.getData().getId() == id) {
            removeFirst();
            return;
        }

        NodeDE temp = head;
        while (temp != null && temp.getData().getId() != id) {
            temp = temp.getNext();
        }

        if (temp != null) {
            if (temp == tail) {
                removeLast();
            } else {
                temp.getPrev().setNext(temp.getNext());
                if (temp.getNext() != null) {
                    temp.getNext().setPrev(temp.getPrev());
                }
            }
            size--;
        }
    }

    // Invertir la lista
    public void invert() {
        if (head != null) {
            NodeDE current = head;
            NodeDE temp = null;

            while (current != null) {
                temp = current.getPrev();
                current.setPrev(current.getNext());
                current.setNext(temp);
                current = current.getPrev();
            }

            if (temp != null) {
                head = temp.getPrev();
            }
        }
    }

    // Intercalar por género
    public void interleaveByGender() {
        if (head == null) return;

        ListDE boys = new ListDE();
        ListDE girls = new ListDE();
        NodeDE temp = head;

        while (temp != null) {
            if (temp.getData().getGender() == 'M') {
                boys.add(temp.getData());
            } else if (temp.getData().getGender() == 'F') {
                girls.add(temp.getData());
            }
            temp = temp.getNext();
        }

        NodeDE boysNode = boys.getHead();
        NodeDE girlsNode = girls.getHead();
        ListDE interleavedList = new ListDE();

        while (boysNode != null || girlsNode != null) {
            if (boysNode != null) {
                interleavedList.add(boysNode.getData());
                boysNode = boysNode.getNext();
            }
            if (girlsNode != null) {
                interleavedList.add(girlsNode.getData());
                girlsNode = girlsNode.getNext();
            }
        }

        this.head = interleavedList.getHead();
        this.tail = interleavedList.getTail();
        this.size = interleavedList.getSize();
    }

    // Intercambiar extremos
    public void swapExtremes() {
        if (head == null || head.getNext() == null) {
            return;
        }

        Kid temp = head.getData();
        head.setData(tail.getData());
        tail.setData(temp);
    }
}
