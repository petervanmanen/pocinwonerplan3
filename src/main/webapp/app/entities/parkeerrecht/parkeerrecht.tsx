import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './parkeerrecht.reducer';

export const Parkeerrecht = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const parkeerrechtList = useAppSelector(state => state.parkeerrecht.entities);
  const loading = useAppSelector(state => state.parkeerrecht.loading);

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
      <h2 id="parkeerrecht-heading" data-cy="ParkeerrechtHeading">
        Parkeerrechts
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/parkeerrecht/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Parkeerrecht
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {parkeerrechtList && parkeerrechtList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('aanmaaktijd')}>
                  Aanmaaktijd <FontAwesomeIcon icon={getSortIconByFieldName('aanmaaktijd')} />
                </th>
                <th className="hand" onClick={sort('bedragaankoop')}>
                  Bedragaankoop <FontAwesomeIcon icon={getSortIconByFieldName('bedragaankoop')} />
                </th>
                <th className="hand" onClick={sort('bedragbtw')}>
                  Bedragbtw <FontAwesomeIcon icon={getSortIconByFieldName('bedragbtw')} />
                </th>
                <th className="hand" onClick={sort('datumtijdeinde')}>
                  Datumtijdeinde <FontAwesomeIcon icon={getSortIconByFieldName('datumtijdeinde')} />
                </th>
                <th className="hand" onClick={sort('datumtijdstart')}>
                  Datumtijdstart <FontAwesomeIcon icon={getSortIconByFieldName('datumtijdstart')} />
                </th>
                <th className="hand" onClick={sort('productnaam')}>
                  Productnaam <FontAwesomeIcon icon={getSortIconByFieldName('productnaam')} />
                </th>
                <th className="hand" onClick={sort('productomschrijving')}>
                  Productomschrijving <FontAwesomeIcon icon={getSortIconByFieldName('productomschrijving')} />
                </th>
                <th>
                  Leverancier Belprovider <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Betreft Voertuig <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {parkeerrechtList.map((parkeerrecht, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/parkeerrecht/${parkeerrecht.id}`} color="link" size="sm">
                      {parkeerrecht.id}
                    </Button>
                  </td>
                  <td>{parkeerrecht.aanmaaktijd}</td>
                  <td>{parkeerrecht.bedragaankoop}</td>
                  <td>{parkeerrecht.bedragbtw}</td>
                  <td>{parkeerrecht.datumtijdeinde}</td>
                  <td>{parkeerrecht.datumtijdstart}</td>
                  <td>{parkeerrecht.productnaam}</td>
                  <td>{parkeerrecht.productomschrijving}</td>
                  <td>
                    {parkeerrecht.leverancierBelprovider ? (
                      <Link to={`/belprovider/${parkeerrecht.leverancierBelprovider.id}`}>{parkeerrecht.leverancierBelprovider.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {parkeerrecht.betreftVoertuig ? (
                      <Link to={`/voertuig/${parkeerrecht.betreftVoertuig.id}`}>{parkeerrecht.betreftVoertuig.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/parkeerrecht/${parkeerrecht.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/parkeerrecht/${parkeerrecht.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/parkeerrecht/${parkeerrecht.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Parkeerrechts found</div>
        )}
      </div>
    </div>
  );
};

export default Parkeerrecht;
