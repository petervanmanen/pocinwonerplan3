import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './landofgebied.reducer';

export const Landofgebied = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const landofgebiedList = useAppSelector(state => state.landofgebied.entities);
  const loading = useAppSelector(state => state.landofgebied.loading);

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
      <h2 id="landofgebied-heading" data-cy="LandofgebiedHeading">
        Landofgebieds
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/landofgebied/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Landofgebied
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {landofgebiedList && landofgebiedList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
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
                <th className="hand" onClick={sort('landcodeiso')}>
                  Landcodeiso <FontAwesomeIcon icon={getSortIconByFieldName('landcodeiso')} />
                </th>
                <th className="hand" onClick={sort('landnaam')}>
                  Landnaam <FontAwesomeIcon icon={getSortIconByFieldName('landnaam')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {landofgebiedList.map((landofgebied, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/landofgebied/${landofgebied.id}`} color="link" size="sm">
                      {landofgebied.id}
                    </Button>
                  </td>
                  <td>
                    {landofgebied.datumeindeland ? (
                      <TextFormat type="date" value={landofgebied.datumeindeland} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {landofgebied.datumingangland ? (
                      <TextFormat type="date" value={landofgebied.datumingangland} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{landofgebied.landcode}</td>
                  <td>{landofgebied.landcodeiso}</td>
                  <td>{landofgebied.landnaam}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/landofgebied/${landofgebied.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/landofgebied/${landofgebied.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/landofgebied/${landofgebied.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Landofgebieds found</div>
        )}
      </div>
    </div>
  );
};

export default Landofgebied;
