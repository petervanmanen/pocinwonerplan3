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

import { getEntities } from './wozobject.reducer';

export const Wozobject = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const wozobjectList = useAppSelector(state => state.wozobject.entities);
  const loading = useAppSelector(state => state.wozobject.loading);

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
      <h2 id="wozobject-heading" data-cy="WozobjectHeading">
        Wozobjects
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/wozobject/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Wozobject
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {wozobjectList && wozobjectList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('empty')}>
                  Empty <FontAwesomeIcon icon={getSortIconByFieldName('empty')} />
                </th>
                <th className="hand" onClick={sort('datumbegingeldigheidwozobject')}>
                  Datumbegingeldigheidwozobject <FontAwesomeIcon icon={getSortIconByFieldName('datumbegingeldigheidwozobject')} />
                </th>
                <th className="hand" onClick={sort('datumeindegeldigheidwozobject')}>
                  Datumeindegeldigheidwozobject <FontAwesomeIcon icon={getSortIconByFieldName('datumeindegeldigheidwozobject')} />
                </th>
                <th className="hand" onClick={sort('datumwaardepeiling')}>
                  Datumwaardepeiling <FontAwesomeIcon icon={getSortIconByFieldName('datumwaardepeiling')} />
                </th>
                <th className="hand" onClick={sort('gebruikscode')}>
                  Gebruikscode <FontAwesomeIcon icon={getSortIconByFieldName('gebruikscode')} />
                </th>
                <th className="hand" onClick={sort('geometriewozobject')}>
                  Geometriewozobject <FontAwesomeIcon icon={getSortIconByFieldName('geometriewozobject')} />
                </th>
                <th className="hand" onClick={sort('grondoppervlakte')}>
                  Grondoppervlakte <FontAwesomeIcon icon={getSortIconByFieldName('grondoppervlakte')} />
                </th>
                <th className="hand" onClick={sort('soortobjectcode')}>
                  Soortobjectcode <FontAwesomeIcon icon={getSortIconByFieldName('soortobjectcode')} />
                </th>
                <th className="hand" onClick={sort('statuswozobject')}>
                  Statuswozobject <FontAwesomeIcon icon={getSortIconByFieldName('statuswozobject')} />
                </th>
                <th className="hand" onClick={sort('vastgesteldewaarde')}>
                  Vastgesteldewaarde <FontAwesomeIcon icon={getSortIconByFieldName('vastgesteldewaarde')} />
                </th>
                <th className="hand" onClick={sort('wozobjectnummer')}>
                  Wozobjectnummer <FontAwesomeIcon icon={getSortIconByFieldName('wozobjectnummer')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {wozobjectList.map((wozobject, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/wozobject/${wozobject.id}`} color="link" size="sm">
                      {wozobject.id}
                    </Button>
                  </td>
                  <td>{wozobject.empty}</td>
                  <td>
                    {wozobject.datumbegingeldigheidwozobject ? (
                      <TextFormat type="date" value={wozobject.datumbegingeldigheidwozobject} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {wozobject.datumeindegeldigheidwozobject ? (
                      <TextFormat type="date" value={wozobject.datumeindegeldigheidwozobject} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {wozobject.datumwaardepeiling ? (
                      <TextFormat type="date" value={wozobject.datumwaardepeiling} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{wozobject.gebruikscode}</td>
                  <td>{wozobject.geometriewozobject}</td>
                  <td>{wozobject.grondoppervlakte}</td>
                  <td>{wozobject.soortobjectcode}</td>
                  <td>{wozobject.statuswozobject}</td>
                  <td>{wozobject.vastgesteldewaarde}</td>
                  <td>{wozobject.wozobjectnummer}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/wozobject/${wozobject.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/wozobject/${wozobject.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/wozobject/${wozobject.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Wozobjects found</div>
        )}
      </div>
    </div>
  );
};

export default Wozobject;
