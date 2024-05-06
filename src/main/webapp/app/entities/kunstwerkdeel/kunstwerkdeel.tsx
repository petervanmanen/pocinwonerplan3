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

import { getEntities } from './kunstwerkdeel.reducer';

export const Kunstwerkdeel = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const kunstwerkdeelList = useAppSelector(state => state.kunstwerkdeel.entities);
  const loading = useAppSelector(state => state.kunstwerkdeel.loading);

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
      <h2 id="kunstwerkdeel-heading" data-cy="KunstwerkdeelHeading">
        Kunstwerkdeels
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/kunstwerkdeel/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Kunstwerkdeel
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {kunstwerkdeelList && kunstwerkdeelList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('datumbegingeldigheidkunstwerkdeel')}>
                  Datumbegingeldigheidkunstwerkdeel <FontAwesomeIcon icon={getSortIconByFieldName('datumbegingeldigheidkunstwerkdeel')} />
                </th>
                <th className="hand" onClick={sort('datumeindegeldigheidkunstwerkdeel')}>
                  Datumeindegeldigheidkunstwerkdeel <FontAwesomeIcon icon={getSortIconByFieldName('datumeindegeldigheidkunstwerkdeel')} />
                </th>
                <th className="hand" onClick={sort('geometriekunstwerkdeel')}>
                  Geometriekunstwerkdeel <FontAwesomeIcon icon={getSortIconByFieldName('geometriekunstwerkdeel')} />
                </th>
                <th className="hand" onClick={sort('identificatiekunstwerkdeel')}>
                  Identificatiekunstwerkdeel <FontAwesomeIcon icon={getSortIconByFieldName('identificatiekunstwerkdeel')} />
                </th>
                <th className="hand" onClick={sort('lod0geometriekunstwerkdeel')}>
                  Lod 0 Geometriekunstwerkdeel <FontAwesomeIcon icon={getSortIconByFieldName('lod0geometriekunstwerkdeel')} />
                </th>
                <th className="hand" onClick={sort('lod1geometriekunstwerkdeel')}>
                  Lod 1 Geometriekunstwerkdeel <FontAwesomeIcon icon={getSortIconByFieldName('lod1geometriekunstwerkdeel')} />
                </th>
                <th className="hand" onClick={sort('lod2geometriekunstwerkdeel')}>
                  Lod 2 Geometriekunstwerkdeel <FontAwesomeIcon icon={getSortIconByFieldName('lod2geometriekunstwerkdeel')} />
                </th>
                <th className="hand" onClick={sort('lod3geometriekunstwerkdeel')}>
                  Lod 3 Geometriekunstwerkdeel <FontAwesomeIcon icon={getSortIconByFieldName('lod3geometriekunstwerkdeel')} />
                </th>
                <th className="hand" onClick={sort('relatievehoogteliggingkunstwerkdeel')}>
                  Relatievehoogteliggingkunstwerkdeel{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('relatievehoogteliggingkunstwerkdeel')} />
                </th>
                <th className="hand" onClick={sort('statuskunstwerkdeel')}>
                  Statuskunstwerkdeel <FontAwesomeIcon icon={getSortIconByFieldName('statuskunstwerkdeel')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {kunstwerkdeelList.map((kunstwerkdeel, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/kunstwerkdeel/${kunstwerkdeel.id}`} color="link" size="sm">
                      {kunstwerkdeel.id}
                    </Button>
                  </td>
                  <td>
                    {kunstwerkdeel.datumbegingeldigheidkunstwerkdeel ? (
                      <TextFormat type="date" value={kunstwerkdeel.datumbegingeldigheidkunstwerkdeel} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {kunstwerkdeel.datumeindegeldigheidkunstwerkdeel ? (
                      <TextFormat type="date" value={kunstwerkdeel.datumeindegeldigheidkunstwerkdeel} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{kunstwerkdeel.geometriekunstwerkdeel}</td>
                  <td>{kunstwerkdeel.identificatiekunstwerkdeel}</td>
                  <td>{kunstwerkdeel.lod0geometriekunstwerkdeel}</td>
                  <td>{kunstwerkdeel.lod1geometriekunstwerkdeel}</td>
                  <td>{kunstwerkdeel.lod2geometriekunstwerkdeel}</td>
                  <td>{kunstwerkdeel.lod3geometriekunstwerkdeel}</td>
                  <td>{kunstwerkdeel.relatievehoogteliggingkunstwerkdeel}</td>
                  <td>{kunstwerkdeel.statuskunstwerkdeel}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/kunstwerkdeel/${kunstwerkdeel.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/kunstwerkdeel/${kunstwerkdeel.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/kunstwerkdeel/${kunstwerkdeel.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Kunstwerkdeels found</div>
        )}
      </div>
    </div>
  );
};

export default Kunstwerkdeel;
