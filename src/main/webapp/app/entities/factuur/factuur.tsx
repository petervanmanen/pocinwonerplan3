import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './factuur.reducer';

export const Factuur = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const factuurList = useAppSelector(state => state.factuur.entities);
  const loading = useAppSelector(state => state.factuur.loading);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        sort: `${sortState.sort},${sortState.order}`,
      }),
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?sort=${sortState.sort},${sortState.order}`;
    if (pageLocation.search !== endURL) {
      navigate(`${pageLocation.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [sortState.order, sortState.sort]);

  const sort = p => () => {
    setSortState({
      ...sortState,
      order: sortState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handleSyncList = () => {
    sortEntities();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = sortState.sort;
    const order = sortState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  return (
    <div>
      <h2 id="factuur-heading" data-cy="FactuurHeading">
        Factuurs
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/factuur/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Factuur
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {factuurList && factuurList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('betaalbaarper')}>
                  Betaalbaarper <FontAwesomeIcon icon={getSortIconByFieldName('betaalbaarper')} />
                </th>
                <th className="hand" onClick={sort('betaaltermijn')}>
                  Betaaltermijn <FontAwesomeIcon icon={getSortIconByFieldName('betaaltermijn')} />
                </th>
                <th className="hand" onClick={sort('code')}>
                  Code <FontAwesomeIcon icon={getSortIconByFieldName('code')} />
                </th>
                <th className="hand" onClick={sort('datumfactuur')}>
                  Datumfactuur <FontAwesomeIcon icon={getSortIconByFieldName('datumfactuur')} />
                </th>
                <th className="hand" onClick={sort('factuurbedragbtw')}>
                  Factuurbedragbtw <FontAwesomeIcon icon={getSortIconByFieldName('factuurbedragbtw')} />
                </th>
                <th className="hand" onClick={sort('factuurbedragexclusiefbtw')}>
                  Factuurbedragexclusiefbtw <FontAwesomeIcon icon={getSortIconByFieldName('factuurbedragexclusiefbtw')} />
                </th>
                <th className="hand" onClick={sort('omschrijving')}>
                  Omschrijving <FontAwesomeIcon icon={getSortIconByFieldName('omschrijving')} />
                </th>
                <th>
                  Schrijftop Kostenplaats <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Gedektvia Inkooporder <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Crediteur Leverancier <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Heeft Debiteur <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {factuurList.map((factuur, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/factuur/${factuur.id}`} color="link" size="sm">
                      {factuur.id}
                    </Button>
                  </td>
                  <td>{factuur.betaalbaarper}</td>
                  <td>{factuur.betaaltermijn}</td>
                  <td>{factuur.code}</td>
                  <td>{factuur.datumfactuur}</td>
                  <td>{factuur.factuurbedragbtw}</td>
                  <td>{factuur.factuurbedragexclusiefbtw}</td>
                  <td>{factuur.omschrijving}</td>
                  <td>
                    {factuur.schrijftopKostenplaats ? (
                      <Link to={`/kostenplaats/${factuur.schrijftopKostenplaats.id}`}>{factuur.schrijftopKostenplaats.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {factuur.gedektviaInkooporder ? (
                      <Link to={`/inkooporder/${factuur.gedektviaInkooporder.id}`}>{factuur.gedektviaInkooporder.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {factuur.crediteurLeverancier ? (
                      <Link to={`/leverancier/${factuur.crediteurLeverancier.id}`}>{factuur.crediteurLeverancier.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {factuur.heeftDebiteur ? <Link to={`/debiteur/${factuur.heeftDebiteur.id}`}>{factuur.heeftDebiteur.id}</Link> : ''}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/factuur/${factuur.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/factuur/${factuur.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/factuur/${factuur.id}/delete`)}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Factuurs found</div>
        )}
      </div>
    </div>
  );
};

export default Factuur;
