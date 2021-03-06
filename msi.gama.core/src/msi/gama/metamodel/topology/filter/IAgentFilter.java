/*******************************************************************************************************
 *
 * msi.gama.metamodel.topology.filter.IAgentFilter.java, in plugin msi.gama.core,
 * is part of the source code of the GAMA modeling and simulation platform (v. 1.8)
 * 
 * (c) 2007-2018 UMI 209 UMMISCO IRD/SU & Partners
 *
 * Visit https://github.com/gama-platform/gama for license information and contacts.
 * 
 ********************************************************************************************************/
package msi.gama.metamodel.topology.filter;

import java.util.Collection;

import msi.gama.metamodel.agent.IAgent;
import msi.gama.metamodel.population.IPopulation;
import msi.gama.metamodel.shape.IShape;
import msi.gama.runtime.IScope;
import msi.gama.util.GamaListFactory;
import msi.gama.util.IContainer;
import msi.gaml.species.ISpecies;

public interface IAgentFilter {

	public static final IAgentFilter NONE = new IAgentFilter() {

		@Override
		public ISpecies getSpecies() {
			return null;
		}

		@Override
		public IContainer<?, ? extends IAgent> getAgents(final IScope scope) {
			return GamaListFactory.create();
		}

		@Override
		public boolean accept(final IScope scope, final IShape source, final IShape a) {
			return true;
		}

		@Override
		public void filter(final IScope scope, final IShape source, final Collection<? extends IShape> results) {}

		@Override
		public IPopulation<? extends IAgent> getPopulation(final IScope scope) {
			return null;
		}
	};

	public ISpecies getSpecies();

	public IPopulation<? extends IAgent> getPopulation(IScope scope);

	public IContainer<?, ? extends IAgent> getAgents(IScope scope);

	boolean accept(IScope scope, IShape source, IShape a);

	public void filter(IScope scope, IShape source, Collection<? extends IShape> results);

}