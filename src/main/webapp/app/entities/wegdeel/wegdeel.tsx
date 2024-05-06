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

import { getEntities } from './wegdeel.reducer';

export const Wegdeel = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const wegdeelList = useAppSelector(state => state.wegdeel.entities);
  const loading = useAppSelector(state => state.wegdeel.loading);

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
      <h2 id="wegdeel-heading" data-cy="WegdeelHeading">
        Wegdeels
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/wegdeel/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Wegdeel
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {wegdeelList && wegdeelList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('datumbegingeldigheidwegdeel')}>
                  Datumbegingeldigheidwegdeel <FontAwesomeIcon icon={getSortIconByFieldName('datumbegingeldigheidwegdeel')} />
                </th>
                <th className="hand" onClick={sort('datumeindegeldigheidwegdeel')}>
                  Datumeindegeldigheidwegdeel <FontAwesomeIcon icon={getSortIconByFieldName('datumeindegeldigheidwegdeel')} />
                </th>
                <th className="hand" onClick={sort('functiewegdeel')}>
                  Functiewegdeel <FontAwesomeIcon icon={getSortIconByFieldName('functiewegdeel')} />
                </th>
                <th className="hand" onClick={sort('fysiekvoorkomenwegdeel')}>
                  Fysiekvoorkomenwegdeel <FontAwesomeIcon icon={getSortIconByFieldName('fysiekvoorkomenwegdeel')} />
                </th>
                <th className="hand" onClick={sort('geometriewegdeel')}>
                  Geometriewegdeel <FontAwesomeIcon icon={getSortIconByFieldName('geometriewegdeel')} />
                </th>
                <th className="hand" onClick={sort('identificatiewegdeel')}>
                  Identificatiewegdeel <FontAwesomeIcon icon={getSortIconByFieldName('identificatiewegdeel')} />
                </th>
                <th className="hand" onClick={sort('kruinlijngeometriewegdeel')}>
                  Kruinlijngeometriewegdeel <FontAwesomeIcon icon={getSortIconByFieldName('kruinlijngeometriewegdeel')} />
                </th>
                <th className="hand" onClick={sort('lod0geometriewegdeel')}>
                  Lod 0 Geometriewegdeel <FontAwesomeIcon icon={getSortIconByFieldName('lod0geometriewegdeel')} />
                </th>
                <th className="hand" onClick={sort('plusfunctiewegdeel')}>
                  Plusfunctiewegdeel <FontAwesomeIcon icon={getSortIconByFieldName('plusfunctiewegdeel')} />
                </th>
                <th className="hand" onClick={sort('plusfysiekvoorkomenwegdeel')}>
                  Plusfysiekvoorkomenwegdeel <FontAwesomeIcon icon={getSortIconByFieldName('plusfysiekvoorkomenwegdeel')} />
                </th>
                <th className="hand" onClick={sort('relatievehoogteliggingwegdeel')}>
                  Relatievehoogteliggingwegdeel <FontAwesomeIcon icon={getSortIconByFieldName('relatievehoogteliggingwegdeel')} />
                </th>
                <th className="hand" onClick={sort('statuswegdeel')}>
                  Statuswegdeel <FontAwesomeIcon icon={getSortIconByFieldName('statuswegdeel')} />
                </th>
                <th className="hand" onClick={sort('wegdeeloptalud')}>
                  Wegdeeloptalud <FontAwesomeIcon icon={getSortIconByFieldName('wegdeeloptalud')} />
                </th>
                <th>
                  Betreft Stremming <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {wegdeelList.map((wegdeel, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/wegdeel/${wegdeel.id}`} color="link" size="sm">
                      {wegdeel.id}
                    </Button>
                  </td>
                  <td>
                    {wegdeel.datumbegingeldigheidwegdeel ? (
                      <TextFormat type="date" value={wegdeel.datumbegingeldigheidwegdeel} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {wegdeel.datumeindegeldigheidwegdeel ? (
                      <TextFormat type="date" value={wegdeel.datumeindegeldigheidwegdeel} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{wegdeel.functiewegdeel}</td>
                  <td>{wegdeel.fysiekvoorkomenwegdeel}</td>
                  <td>{wegdeel.geometriewegdeel}</td>
                  <td>{wegdeel.identificatiewegdeel}</td>
                  <td>{wegdeel.kruinlijngeometriewegdeel}</td>
                  <td>{wegdeel.lod0geometriewegdeel}</td>
                  <td>{wegdeel.plusfunctiewegdeel}</td>
                  <td>{wegdeel.plusfysiekvoorkomenwegdeel}</td>
                  <td>{wegdeel.relatievehoogteliggingwegdeel}</td>
                  <td>{wegdeel.statuswegdeel}</td>
                  <td>{wegdeel.wegdeeloptalud}</td>
                  <td>
                    {wegdeel.betreftStremmings
                      ? wegdeel.betreftStremmings.map((val, j) => (
                          <span key={j}>
                            <Link to={`/stremming/${val.id}`}>{val.id}</Link>
                            {j === wegdeel.betreftStremmings.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/wegdeel/${wegdeel.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/wegdeel/${wegdeel.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/wegdeel/${wegdeel.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Wegdeels found</div>
        )}
      </div>
    </div>
  );
};

export default Wegdeel;