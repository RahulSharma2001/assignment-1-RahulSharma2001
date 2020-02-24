package AD;

public interface Cont<E> {
    void add(E item);

    E remove();

    E getData(int index);
}
package AD;

public interface MyCon {
    void addContact();

    void vContact();

    void delContact();

    void searchContact();
}
package def;

        import AD.MyCon;

        import java.util.InputMismatchException;
        import java.util.Scanner;
        import java.util.regex.Pattern;

public class MyContact implements MyCon {
    Scanner sc = new Scanner(System.in);
    MyLinked<Person> MyContactBook = new MyLinked<>();
    MyLinked<String> contactNumbers = new MyLinked<>();

    private String getFirstName() {
        System.out.println("Please Enter The Name Of The Person ");
        System.out.println("FirstName: ");
        String firstName = sc.next();
        return firstName;
    }

    private String getLastName() {
        System.out.println("LastName: ");
        String lastName = sc.next();
        return lastName;
    }

    private MyLinked<String> GetContactNumbers() {
        MyLinked<String> contactNumbers = new MyLinked<>();
        System.out.println("Contact Number");
        String contactNum = sc.next();
        while (true) {
            if (Pattern.matches("[0-9]+", contactNum)) {
                contactNumbers.add(contactNum);
                break;
            } else {
                System.out.println("Invalid PhoneNumber");
                break;
            }
        }
        while (true) {
            System.out.println("Do You want to add new Contact Number? (y/n) :");
            String s = sc.next();
            char at = s.charAt(0);
            if (s.length() > 1) {
                System.out.println("Please enter a valid input i.e. , y(lowercase) for Yes or n(lowercase for No)");
                continue;
            }
            if (at == 'y') {
                System.out.println("contactNumber: ");
                contactNum = sc.next();
                if (Pattern.matches("[0-9]+", contactNum)) {
                    contactNumbers.add(contactNum);
                } else {
                    System.out.println("Invalid PhoneNumber");
                }
            } else if (at == 'n') {
                break;
            } else {
                System.out.println("Please Enter a Vaild Input i.e., y(lowercase) for Yes or n(lowercase) for NO");
            }
        }
        return contactNumbers;

    }


    private String getEmail() {
        String Email = null;
        while (true) {
            System.out.println("Do you want to add an Email ? (y/n) :");
            String s = sc.next();
            char at = s.charAt(0);
            if (s.length() > 1) {
                System.out.println("Please Enter a valid input i.e.,y(lowerCase) for Yes or n(lowerCase for NO)");
                continue;
            }
            if (at == 'y') {
                System.out.println("Email Address: ");
                Email = sc.next();
            } else if (at == 'n') {
                break;
            } else {
                System.out.println("Please Enter Valid Input i.e., y(lowecase) for Yes or n(lowercase) for NO");
            }
        }
        return Email;
    }

    private int compareFirstName(String fName) {
        int index = 0;
        if (MyContactBook.size == 0) {
        } else {
            for (int i = 0; i < MyContactBook.size; i++) {
                Person temp = MyContactBook.getData(i);
                String name = temp.getFirstName();
                name = name.toLowerCase();
                fName = fName.toLowerCase();
                if (name.compareTo(fName) < 0) {
                    index++;
                } else if (name.compareTo(fName) == 0) {
                    return index;

                } else {
                    break;
                }

            }

        }
        return index;
    }
    public void addContact() {
        System.out.println("You have chosen to add a new contact:");
        String firstname = getFirstName();
        String lastname = getLastName();
        contactNumbers = GetContactNumbers();
        String Email = getEmail();
        Person newContact;
        newContact = new Person(firstname, lastname, Email, contactNumbers);
        int index = compareFirstName(firstname);
        MyContactBook.add(newContact, index);
        System.out.println("Contact Added SucessFully");
        System.out.println();
        System.out.println();


    }
    public void vContact() {
        System.out.println("---Here are all your contacts---\n" );

        for (int i = 0; i < MyContactBook.size; i++) {
            Person response = MyContactBook.getData(i);
            System.out.println(response);
        }
        System.out.println("Total Contacts: " + MyContactBook.size);

    }

    private void printNames() {
        System.out.println("Here are your all contacts:");
        for (int i = 0; i < MyContactBook.size; i++) {
            Person temp = MyContactBook.getData(i);
            System.out.println((i + 1) + "." + temp.getFirstName() + " " + temp.getLastName());
        }
    }
    public void delContact() {
        Scanner sc = new Scanner(System.in);
        printNames();
        System.out.print("Press the number against the contact to delete it: ");
        try {
            int index = sc.nextInt();
            if (index > MyContactBook.size || index == 0) {
                System.out.println("Invaild Input");
            } else {
                Person p = MyContactBook.getData(index - 1);
                String name = p.getFirstName() + p.getLastName();
                MyContactBook.remove(index - 1);
                System.out.println(name + "'s Contact has been removed Successfully");
            }
        } catch (InputMismatchException E) {
            System.out.println("Integer input expected ");
        }

    }

   
    public void searchContact() {
        int size = 0;
        Scanner sc = new Scanner(System.in);
        System.out.println("You could search for a contact from their first names:");
        String name = sc.next();
        name = name.trim();
        MyLinked<Integer> lists = matchFirst(name);
        boolean a = false;
        size = lists.size;
        if (size > 1) {
            a = true;
        }

        System.out.println(a ? size + " Matches found!" : size + " Match found!");
        for (int i = 0; i < size; i++) {
            int index = lists.getData(i);
            System.out.println(MyContactBook.getData(index));
        }

    }

    private MyLinked<Integer> matchFirst(String Firstname) {
        MyLinked<Integer> indexes = new MyLinked<>();
        if (MyContactBook.size == 0) {
        } else {
            for (int i = 0; i < MyContactBook.size; i++) {
                Person temp = MyContactBook.getData(i);
                String name = temp.getFirstName();
                name = name.toLowerCase();
                Firstname = Firstname.toLowerCase();

                if (name.compareTo(Firstname) == 0) {
                    indexes.add(i);
                }

            }
        }
        return indexes;
    }
}
package def;

        import AD.Cont;

public class MyLinked<E> implements Cont<E> {
    private Node<E> head = null;
    public int size = 0;

    private void addFirst(E item) {
        head = new Node<>(item, head);
        size++;
    }

    private void addAfter(E item, Node<E> afterNode) {
        afterNode.next = new Node<>(item, afterNode.next);
        size++;
    }

    public void add(E item, int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException(Integer.toString(index));
        } else if (index == 0) {
            addFirst(item);
        } else {
            addAfter(item, getNode(index - 1));
        }
    }

    private Node<E> getNode(int index) {
        Node<E> response = head;
        for (int i = 0; i < index; i++) {
            response = response.getNext();
        }
        return response;
    }

    private E removeFirst() {
        Node<E> temp = head;
        E response = null;
        if (head == null) {
            head = head.getNext();
        }
        if (temp != null) {
            size--;
            response = temp.getData();
        }
        return response;
    }

    private E removeAfter(Node<E> afterNode) {
        Node<E> temp = afterNode.getNext();
        if (temp != null) {
            afterNode.next = temp.getNext();
            size--;
        }
        E response = temp.getData();
        return response;
    }

    public E remove(int index) {
        E response = null;
        if (index > 0 || index < size) {
            throw new IndexOutOfBoundsException(Integer.toString(index));
        } else if (index == 0) {
            response = removeFirst();
        } else {
            Node<E> previousNode = getNode(index - 1);
            response = removeAfter(previousNode);
        }
        return response;
    }


    public void add(E item) {
        add(item, size);
    }


    public E remove() {
        return remove(size - 1);
    }


    public E getData(int index) {
        return getNode(index).getData();
    }

    private static class Node<E> {
        private E data;
        private Node<E> next;

        public E getData() {
            return data;
        }

        public Node<E> getNext() {
            return next;
        }

        public Node(E data, Node<E> next) {
            this.data = data;
            this.next = next;
        }
    }
}
 package def;

public class Person {
    private String firstName;
    private String lastName;
    private String email;
    private MyLinked<String> ContactNumber;


    public Person(String firstName, String lastName, String email, MyLinked<String> contactNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        ContactNumber = contactNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getContactNumber() {
        StringBuilder contacts = new StringBuilder();
        boolean p = false;
        for (int i = 0; i < ContactNumber.size; i++) {
            if (i == 0) {
                contacts.append(ContactNumber.getData(i));
            } else {
                contacts.append(",").append(ContactNumber.getData(i));
            }
        }
        if (ContactNumber.size == 0) {
            contacts.append("null");

        } else if (ContactNumber.size == 1) {
            p = true;
        }
        return p ? "Contact" + contacts : "contacts" + contacts;
    }


    public String toString() {
        return "firstName:" + getFirstName() + "\n" + "lastName:" + getLastName() + "\n" + getContactNumber() + "\n" + "email:" + getEmail());
    }
}
package execution;

        import def.MyContact;

        import java.util.Scanner;

public class MyMain {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MyContact user = new MyContact();
        boolean response = true;
        do {
            System.out.println("Welcome to Astha's Contact List App\n" +
                    "Press 1 to add a new contact\n" +
                    "Press 2 to view all contacts\n" +
                    "Press 3 to search for a contact\n" +
                    "Press 4 to delete a contact\n" +
                    "Press 5 to exit program ");
            String st = sc.next();
            if (st.length() > 1) {
                System.out.println("Invalid Option");
                continue;
            }
            char choice = st.charAt(0);
            switch (choice) {
                case '1':
                    user.addContact();
                    break;
                case '2':
                    user.vContact();
                    break;
                case '3':
                    user.searchContact();
                    break;
                case '4':
                    user.delContact();
                    break;
                case '5':
                    System.out.println("Thank You");
                    System.out.println("Have a great day");
                    response = false;
                    break;
                default:
                    System.out.println("Invalid Option");
            }
        }
        while (response);
        sc.close();
    }
}