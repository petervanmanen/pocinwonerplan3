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

import { getEntities } from './waterdeel.reducer';

export const Waterdeel = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const waterdeelList = useAppSelector(state => state.waterdeel.entities);
  const loading = useAppSelector(state => state.waterdeel.loading);

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
      <h2 id="waterdeel-heading" data-cy="WaterdeelHeading">
        Waterdeels
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/waterdeel/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Waterdeel
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {waterdeelList && waterdeelList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('datumbegingeldigheidwaterdeel')}>
                  Datumbegingeldigheidwaterdeel <FontAwesomeIcon icon={getSortIconByFieldName('datumbegingeldigheidwaterdeel')} />
                </th>
                <th className="hand" onClick={sort('datumeindegeldigheidwaterdeel')}>
                  Datumeindegeldigheidwaterdeel <FontAwesomeIcon icon={getSortIconByFieldName('datumeindegeldigheidwaterdeel')} />
                </th>
                <th className="hand" onClick={sort('geometriewaterdeel')}>
                  Geometriewaterdeel <FontAwesomeIcon icon={getSortIconByFieldName('geometriewaterdeel')} />
                </th>
                <th className="hand" onClick={sort('identificatiewaterdeel')}>
                  Identificatiewaterdeel <FontAwesomeIcon icon={getSortIconByFieldName('identificatiewaterdeel')} />
                </th>
                <th className="hand" onClick={sort('plustypewaterdeel')}>
                  Plustypewaterdeel <FontAwesomeIcon icon={getSortIconByFieldName('plustypewaterdeel')} />
                </th>
                <th className="hand" onClick={sort('relatievehoogteliggingwaterdeel')}>
                  Relatievehoogteliggingwaterdeel <FontAwesomeIcon icon={getSortIconByFieldName('relatievehoogteliggingwaterdeel')} />
                </th>
                <th className="hand" onClick={sort('statuswaterdeel')}>
                  Statuswaterdeel <FontAwesomeIcon icon={getSortIconByFieldName('statuswaterdeel')} />
                </th>
                <th className="hand" onClick={sort('typewaterdeel')}>
                  Typewaterdeel <FontAwesomeIcon icon={getSortIconByFieldName('typewaterdeel')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {waterdeelList.map((waterdeel, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/waterdeel/${waterdeel.id}`} color="link" size="sm">
                      {waterdeel.id}
                    </Button>
                  </td>
                  <td>
                    {waterdeel.datumbegingeldigheidwaterdeel ? (
                      <TextFormat type="date" value={waterdeel.datumbegingeldigheidwaterdeel} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {waterdeel.datumeindegeldigheidwaterdeel ? (
                      <TextFormat type="date" value={waterdeel.datumeindegeldigheidwaterdeel} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{waterdeel.geometriewaterdeel}</td>
                  <td>{waterdeel.identificatiewaterdeel}</td>
                  <td>{waterdeel.plustypewaterdeel}</td>
                  <td>{waterdeel.relatievehoogteliggingwaterdeel}</td>
                  <td>{waterdeel.statuswaterdeel}</td>
                  <td>{waterdeel.typewaterdeel}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/waterdeel/${waterdeel.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/waterdeel/${waterdeel.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/waterdeel/${waterdeel.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Waterdeels found</div>
        )}
      </div>
    </div>
  );
};

export default Waterdeel;
