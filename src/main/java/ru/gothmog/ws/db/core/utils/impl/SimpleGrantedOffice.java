package ru.gothmog.ws.db.core.utils.impl;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.Assert;
import ru.gothmog.ws.db.core.utils.GrantedOffice;

public class SimpleGrantedOffice implements GrantedOffice {

    private final String office;

    public SimpleGrantedOffice(String office) {
        Assert.hasText(office, "A granted office textual representation is required");
        this.office = office;
    }

    @Override
    public String getOffice() {
        return this.office;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof SimpleGrantedAuthority) {
            return this.office.equals(((SimpleGrantedOffice) obj).office);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.office.hashCode();
    }

    @Override
    public String toString() {
        return this.office;
    }
}
