package states;

import interfaces.state.BinaryState;
import interfaces.state.State;

import java.util.BitSet;
import java.util.Objects;
import java.util.stream.IntStream;

public class ImmutableBinaryState implements BinaryState{

    private final BitSet state;
    private final int bitsNumber;

    /**
     * Constructs an empty BinaryState
     * @param bitsNumber
     */
    public ImmutableBinaryState(int bitsNumber) {
        this.state = new BitSet(bitsNumber); // creates a NEW BitSet!
        this.bitsNumber = bitsNumber;
    }

    /**
     * Copy constructor passing a BitSet object
     * @param bitsNumber
     * @param bitsetState
     */
    public ImmutableBinaryState(int bitsNumber, BitSet bitsetState) {
        this(bitsNumber);
        this.state.or(bitsetState);
    }

    /**
     * Copy constructor
     * @param bitsNumber
     * @param binaryState
     */
    public ImmutableBinaryState(int bitsNumber, ImmutableBinaryState binaryState) {
        this(bitsNumber, binaryState.state);
    }

    /**
     * Creates a state with the specified bit's indices to 1.
     * @param bitsNumber
     * @param indices
     */
    public ImmutableBinaryState(int bitsNumber, int... indices) {
        this(bitsNumber);
        for (int index : indices){
            this.state.set(index);
        }
    }

    /**
     * Creates a copy of the state and FLIPS a node.
     */
    private ImmutableBinaryState(int bitsNumber, ImmutableBinaryState binaryState, int indexToFlip) {
        this(bitsNumber, binaryState);
        this.state.flip(indexToFlip);
    }

    /**
     * Creates a copy of the state and FLIPS more than one nodes.
     */
    private ImmutableBinaryState(int bitsNumber, ImmutableBinaryState binaryState, Integer... indicesToFlip) {
        this(bitsNumber, binaryState);
        for (Integer index : indicesToFlip) {
            this.state.flip(index);
        }
    }

    @Override
    public Boolean getNodeValue(Integer index) {
        return this.state.get(index);
    }

    /**
     * Returns a NEW BitSet object that represents the copy of this state.
     * @return
     */
    @Override
    public BitSet toBitSet() {
        BitSet newState = new BitSet();
        newState.or(this.state);
        return newState;
    }

    /*@Override
    public BinaryState flipNodesValues(Integer index1) {
        BitSet newState = new BitSet();
        newState.or(this.state);

        newState.flip(index1);

        return new ImmutableBinaryState(this.bitsNumber, newState);
    }*/

    @Override
    public BinaryState flipNodesValues(Integer index1) {
        return new ImmutableBinaryState(this.bitsNumber, this, index1);
    }

    @Override
    public BinaryState flipNodesValues(Integer... indices) {
        return new ImmutableBinaryState(this.bitsNumber, this, indices);
    }

    /*@Override
    public BinaryState flipNodesValues(Integer... indices) {
        BitSet newState = new BitSet();
        newState.or(this.state);
        for (Integer index : indices) {
            newState.flip(index);
        }
        return new ImmutableBinaryState(this.bitsNumber, newState);
    }*/

    @Override
    public String getStringRepresentation() {
        final StringBuilder buffer = new StringBuilder(this.bitsNumber);
        IntStream.range(0, this.bitsNumber).mapToObj(i -> this.state.get((this.bitsNumber-1)-i) ? '1' : '0').forEach(buffer::append);
        return buffer.toString();
    }

    @Override
    public Integer getLength() {
        return bitsNumber;
    }


    @Override
    public int compareTo(State o) {
        if (Objects.isNull(o)) {
            throw new NullPointerException();
        }

        ImmutableBinaryState that = (ImmutableBinaryState) o;

        int length = (this.bitsNumber > that.bitsNumber ? this.bitsNumber : that.bitsNumber);
        for(; length >= 0; length--){
            if((this.state.get(length) ^ that.state.get(length))) { //finché i bit sono uguali non faccio nulla, ^ = XOR
                if (this.state.get(length)){ // se sono diversi e this è a uno allora lui è più grande
                    return 1;
                }
                return -1;
            }
        }
        return 0;
    }





    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImmutableBinaryState that = (ImmutableBinaryState) o;
        return bitsNumber == that.bitsNumber &&
                Objects.equals(state, that.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(state, bitsNumber);
    }

    @Override
    public String toString() {
        return "s:" +
                getStringRepresentation();
                //" <#" + bitsNumber +
                //'>';
    }
    public static void main(String [] args) {
        BinaryState b = new ImmutableBinaryState(10, 1 , 3);
        System.out.println(b);

        BinaryState c = b.flipNodesValues(1);
        System.out.println(b);
        System.out.println(c);


        BinaryState d = new ImmutableBinaryState(10, 1 , 3);

        System.out.println("==: " + (d == b));
        System.out.println("equals: " + (d.equals(b)));

        BitSet copy = d.toBitSet();
        copy.set(9);
        System.out.println(d);

        BinaryState f = new ImmutableBinaryState(10, copy);
        System.out.println(f);

        System.out.println("==: " + (d == f));



    }


}
