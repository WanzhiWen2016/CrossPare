// Copyright 2015 Georg-August-Universität Göttingen, Germany
//
//   Licensed under the Apache License, Version 2.0 (the "License");
//   you may not use this file except in compliance with the License.
//   You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//   Unless required by applicable law or agreed to in writing, software
//   distributed under the License is distributed on an "AS IS" BASIS,
//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//   See the License for the specific language governing permissions and
//   limitations under the License.

package de.ugoe.cs.cpdp.versions;

import java.util.List;

import weka.core.Instances;

/**
 * Removes unbalanced data sets in terms of classification. All data sets that are outside of the
 * quantil defined by setParameter (default=0.1) are removed.
 * 
 * @author Steffen Herbold
 */
public class UnbalancedFilter implements IVersionFilter {

    /**
     * quantil where outside lying versions are removed
     */
    private double quantil = 0.1;

    /**
     * Sets the quantil.
     * 
     * @param parameters
     *            the quantil as string
     */
    @Override
    public void setParameter(String parameters) {
        this.quantil = Double.parseDouble(parameters);
    }

    /**
     * @see de.ugoe.cs.cpdp.versions.IVersionFilter#apply(de.ugoe.cs.cpdp.versions.SoftwareVersion)
     */
    @Override
    public boolean apply(SoftwareVersion version, List<SoftwareVersion> allVersions) {
        final Instances instances = version.getInstances();

        int count = 0;
        for(int i=0; i<instances.size(); i++) {
        	if(instances.get(i).classValue()>0) {
        		count++;
        	}
        }
        return ((double) count) / instances.numInstances() >= (1 - this.quantil) ||
            ((double) count) / instances.numInstances() <= (this.quantil);
    }

}
