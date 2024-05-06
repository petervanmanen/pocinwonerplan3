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

import { getEntities } from './tunneldeel.reducer';

export const Tunneldeel = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const tunneldeelList = useAppSelector(state => state.tunneldeel.entities);
  const loading = useAppSelector(state => state.tunneldeel.loading);

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
      <h2 id="tunneldeel-heading" data-cy="TunneldeelHeading">
        Tunneldeels
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/tunneldeel/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Tunneldeel
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {tunneldeelList && tunneldeelList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('datumbegingeldigheidtunneldeel')}>
                  Datumbegingeldigheidtunneldeel <FontAwesomeIcon icon={getSortIconByFieldName('datumbegingeldigheidtunneldeel')} />
                </th>
                <th className="hand" onClick={sort('datumeindegeldigheidtunneldeel')}>
                  Datumeindegeldigheidtunneldeel <FontAwesomeIcon icon={getSortIconByFieldName('datumeindegeldigheidtunneldeel')} />
                </th>
                <th className="hand" onClick={sort('geometrietunneldeel')}>
                  Geometrietunneldeel <FontAwesomeIcon icon={getSortIconByFieldName('geometrietunneldeel')} />
                </th>
                <th className="hand" onClick={sort('identificatietunneldeel')}>
                  Identificatietunneldeel <FontAwesomeIcon icon={getSortIconByFieldName('identificatietunneldeel')} />
                </th>
                <th className="hand" onClick={sort('lod0geometrietunneldeel')}>
                  Lod 0 Geometrietunneldeel <FontAwesomeIcon icon={getSortIconByFieldName('lod0geometrietunneldeel')} />
                </th>
                <th className="hand" onClick={sort('relatievehoogteliggingtunneldeel')}>
                  Relatievehoogteliggingtunneldeel <FontAwesomeIcon icon={getSortIconByFieldName('relatievehoogteliggingtunneldeel')} />
                </th>
                <th className="hand" onClick={sort('statustunneldeel')}>
                  Statustunneldeel <FontAwesomeIcon icon={getSortIconByFieldName('statustunneldeel')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {tunneldeelList.map((tunneldeel, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/tunneldeel/${tunneldeel.id}`} color="link" size="sm">
                      {tunneldeel.id}
                    </Button>
                  </td>
                  <td>
                    {tunneldeel.datumbegingeldigheidtunneldeel ? (
                      <TextFormat type="date" value={tunneldeel.datumbegingeldigheidtunneldeel} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {tunneldeel.datumeindegeldigheidtunneldeel ? (
                      <TextFormat type="date" value={tunneldeel.datumeindegeldigheidtunneldeel} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{tunneldeel.geometrietunneldeel}</td>
                  <td>{tunneldeel.identificatietunneldeel}</td>
                  <td>{tunneldeel.lod0geometrietunneldeel}</td>
                  <td>{tunneldeel.relatievehoogteliggingtunneldeel}</td>
                  <td>{tunneldeel.statustunneldeel}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/tunneldeel/${tunneldeel.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/tunneldeel/${tunneldeel.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/tunneldeel/${tunneldeel.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Tunneldeels found</div>
        )}
      </div>
    </div>
  );
};

export default Tunneldeel;
