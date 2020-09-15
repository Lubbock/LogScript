package com.seven.log.calc;


import static com.google.common.base.Preconditions.*;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import lombok.Data;

import java.nio.charset.Charset;
import java.util.BitSet;

/**
 * <p>布隆过滤器</p>
 **/
@Data
public class BloomFilterService {
    private int element;
    private double ep;
    BloomFilter<CharSequence> filter;

    static BloomFilterService filterService = null;

    public BloomFilterService(int element, double ep) {
        this.element = element;
        this.ep = ep;
        filter = BloomFilter.create(Funnels.stringFunnel(Charset.forName("utf-8")), element, 0.1);
    }

    public void put(String element) {
        filter.put(element);
    }

    public boolean putIfNotPresent(String element) {
        boolean isPresenet = filter.mightContain(element);
        if (!isPresenet) {
            put(element);
        }
        return isPresenet;
    }

    public static BloomFilterService getInstance() {
        if (filterService == null) {
            filterService = new BloomFilterService(100, 0.1);
        }
        return filterService;
    }
}
