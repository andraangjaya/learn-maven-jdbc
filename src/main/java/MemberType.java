public enum MemberType {
    NO_MEMBER("N"),
    BRONZE("B"),
    SILVER("S"),
    GOLD("G");

    private String value;

    MemberType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
