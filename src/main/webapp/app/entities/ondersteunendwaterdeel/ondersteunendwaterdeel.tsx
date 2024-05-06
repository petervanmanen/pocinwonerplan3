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

import { getEntities } from './ondersteunendwaterdeel.reducer';

export const Ondersteunendwaterdeel = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const ondersteunendwaterdeelList = useAppSelector(state => state.ondersteunendwaterdeel.entities);
  const loading = useAppSelector(state => state.ondersteunendwaterdeel.loading);

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
      <h2 id="ondersteunendwaterdeel-heading" data-cy="OndersteunendwaterdeelHeading">
        Ondersteunendwaterdeels
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/ondersteunendwaterdeel/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Ondersteunendwaterdeel
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {ondersteunendwaterdeelList && ondersteunendwaterdeelList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('datumbegingeldigheidondersteunendwaterdeel')}>
                  Datumbegingeldigheidondersteunendwaterdeel{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('datumbegingeldigheidondersteunendwaterdeel')} />
                </th>
                <th className="hand" onClick={sort('datumeindegeldigheidondersteunendwaterdeel')}>
                  Datumeindegeldigheidondersteunendwaterdeel{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('datumeindegeldigheidondersteunendwaterdeel')} />
                </th>
                <th className="hand" onClick={sort('geometrieondersteunendwaterdeel')}>
                  Geometrieondersteunendwaterdeel <FontAwesomeIcon icon={getSortIconByFieldName('geometrieondersteunendwaterdeel')} />
                </th>
                <th className="hand" onClick={sort('identificatieondersteunendwaterdeel')}>
                  Identificatieondersteunendwaterdeel{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('identificatieondersteunendwaterdeel')} />
                </th>
                <th className="hand" onClick={sort('plustypeondersteunendwaterdeel')}>
                  Plustypeondersteunendwaterdeel <FontAwesomeIcon icon={getSortIconByFieldName('plustypeondersteunendwaterdeel')} />
                </th>
                <th className="hand" onClick={sort('relatievehoogteliggingondersteunendwaterdeel')}>
                  Relatievehoogteliggingondersteunendwaterdeel{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('relatievehoogteliggingondersteunendwaterdeel')} />
                </th>
                <th className="hand" onClick={sort('statusondersteunendwaterdeel')}>
                  Statusondersteunendwaterdeel <FontAwesomeIcon icon={getSortIconByFieldName('statusondersteunendwaterdeel')} />
                </th>
                <th className="hand" onClick={sort('typeondersteunendwaterdeel')}>
                  Typeondersteunendwaterdeel <FontAwesomeIcon icon={getSortIconByFieldName('typeondersteunendwaterdeel')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {ondersteunendwaterdeelList.map((ondersteunendwaterdeel, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/ondersteunendwaterdeel/${ondersteunendwaterdeel.id}`} color="link" size="sm">
                      {ondersteunendwaterdeel.id}
                    </Button>
                  </td>
                  <td>
                    {ondersteunendwaterdeel.datumbegingeldigheidondersteunendwaterdeel ? (
                      <TextFormat
                        type="date"
                        value={ondersteunendwaterdeel.datumbegingeldigheidondersteunendwaterdeel}
                        format={APP_LOCAL_DATE_FORMAT}
                      />
                    ) : null}
                  </td>
                  <td>
                    {ondersteunendwaterdeel.datumeindegeldigheidondersteunendwaterdeel ? (
                      <TextFormat
                        type="date"
                        value={ondersteunendwaterdeel.datumeindegeldigheidondersteunendwaterdeel}
                        format={APP_LOCAL_DATE_FORMAT}
                      />
                    ) : null}
                  </td>
                  <td>{ondersteunendwaterdeel.geometrieondersteunendwaterdeel}</td>
                  <td>{ondersteunendwaterdeel.identificatieondersteunendwaterdeel}</td>
                  <td>{ondersteunendwaterdeel.plustypeondersteunendwaterdeel}</td>
                  <td>{ondersteunendwaterdeel.relatievehoogteliggingondersteunendwaterdeel}</td>
                  <td>{ondersteunendwaterdeel.statusondersteunendwaterdeel}</td>
                  <td>{ondersteunendwaterdeel.typeondersteunendwaterdeel}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/ondersteunendwaterdeel/${ondersteunendwaterdeel.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/ondersteunendwaterdeel/${ondersteunendwaterdeel.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/ondersteunendwaterdeel/${ondersteunendwaterdeel.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Ondersteunendwaterdeels found</div>
        )}
      </div>
    </div>
  );
};

export default Ondersteunendwaterdeel;
