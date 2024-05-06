import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './land.reducer';

export const Land = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const landList = useAppSelector(state => state.land.entities);
  const loading = useAppSelector(state => state.land.loading);

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
      <h2 id="land-heading" data-cy="LandHeading">
        Lands
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/land/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Land
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {landList && landList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('datumeindefictief')}>
                  Datumeindefictief <FontAwesomeIcon icon={getSortIconByFieldName('datumeindefictief')} />
                </th>
                <th className="hand" onClick={sort('datumeindeland')}>
                  Datumeindeland <FontAwesomeIcon icon={getSortIconByFieldName('datumeindeland')} />
                </th>
                <th className="hand" onClick={sort('datumingangland')}>
                  Datumingangland <FontAwesomeIcon icon={getSortIconByFieldName('datumingangland')} />
                </th>
                <th className="hand" onClick={sort('landcode')}>
                  Landcode <FontAwesomeIcon icon={getSortIconByFieldName('landcode')} />
                </th>
                <th className="hand" onClick={sort('landcodeisodrieletterig')}>
                  Landcodeisodrieletterig <FontAwesomeIcon icon={getSortIconByFieldName('landcodeisodrieletterig')} />
                </th>
                <th className="hand" onClick={sort('landcodeisotweeletterig')}>
                  Landcodeisotweeletterig <FontAwesomeIcon icon={getSortIconByFieldName('landcodeisotweeletterig')} />
                </th>
                <th className="hand" onClick={sort('landnaam')}>
                  Landnaam <FontAwesomeIcon icon={getSortIconByFieldName('landnaam')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {landList.map((land, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/land/${land.id}`} color="link" size="sm">
                      {land.id}
                    </Button>
                  </td>
                  <td>{land.datumeindefictief ? 'true' : 'false'}</td>
                  <td>{land.datumeindeland}</td>
                  <td>{land.datumingangland}</td>
                  <td>{land.landcode}</td>
                  <td>{land.landcodeisodrieletterig}</td>
                  <td>{land.landcodeisotweeletterig}</td>
                  <td>{land.landnaam}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/land/${land.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/land/${land.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/land/${land.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Lands found</div>
        )}
      </div>
    </div>
  );
};

export default Land;
