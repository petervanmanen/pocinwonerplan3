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

import { getEntities } from './overigbouwwerk.reducer';

export const Overigbouwwerk = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const overigbouwwerkList = useAppSelector(state => state.overigbouwwerk.entities);
  const loading = useAppSelector(state => state.overigbouwwerk.loading);

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
      <h2 id="overigbouwwerk-heading" data-cy="OverigbouwwerkHeading">
        Overigbouwwerks
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/overigbouwwerk/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Overigbouwwerk
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {overigbouwwerkList && overigbouwwerkList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('datumbegingeldigheidoverigbouwwerk')}>
                  Datumbegingeldigheidoverigbouwwerk <FontAwesomeIcon icon={getSortIconByFieldName('datumbegingeldigheidoverigbouwwerk')} />
                </th>
                <th className="hand" onClick={sort('datumeindegeldigheidoverigbouwwerk')}>
                  Datumeindegeldigheidoverigbouwwerk <FontAwesomeIcon icon={getSortIconByFieldName('datumeindegeldigheidoverigbouwwerk')} />
                </th>
                <th className="hand" onClick={sort('geometrieoverigbouwwerk')}>
                  Geometrieoverigbouwwerk <FontAwesomeIcon icon={getSortIconByFieldName('geometrieoverigbouwwerk')} />
                </th>
                <th className="hand" onClick={sort('identificatieoverigbouwwerk')}>
                  Identificatieoverigbouwwerk <FontAwesomeIcon icon={getSortIconByFieldName('identificatieoverigbouwwerk')} />
                </th>
                <th className="hand" onClick={sort('lod0geometrieoverigbouwwerk')}>
                  Lod 0 Geometrieoverigbouwwerk <FontAwesomeIcon icon={getSortIconByFieldName('lod0geometrieoverigbouwwerk')} />
                </th>
                <th className="hand" onClick={sort('lod1geometrieoverigbouwwerk')}>
                  Lod 1 Geometrieoverigbouwwerk <FontAwesomeIcon icon={getSortIconByFieldName('lod1geometrieoverigbouwwerk')} />
                </th>
                <th className="hand" onClick={sort('lod2geometrieoverigbouwwerk')}>
                  Lod 2 Geometrieoverigbouwwerk <FontAwesomeIcon icon={getSortIconByFieldName('lod2geometrieoverigbouwwerk')} />
                </th>
                <th className="hand" onClick={sort('lod3geometrieoverigbouwwerk')}>
                  Lod 3 Geometrieoverigbouwwerk <FontAwesomeIcon icon={getSortIconByFieldName('lod3geometrieoverigbouwwerk')} />
                </th>
                <th className="hand" onClick={sort('relatievehoogteliggingoverigbouwwerk')}>
                  Relatievehoogteliggingoverigbouwwerk{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('relatievehoogteliggingoverigbouwwerk')} />
                </th>
                <th className="hand" onClick={sort('statusoverigbouwwerk')}>
                  Statusoverigbouwwerk <FontAwesomeIcon icon={getSortIconByFieldName('statusoverigbouwwerk')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {overigbouwwerkList.map((overigbouwwerk, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/overigbouwwerk/${overigbouwwerk.id}`} color="link" size="sm">
                      {overigbouwwerk.id}
                    </Button>
                  </td>
                  <td>
                    {overigbouwwerk.datumbegingeldigheidoverigbouwwerk ? (
                      <TextFormat type="date" value={overigbouwwerk.datumbegingeldigheidoverigbouwwerk} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {overigbouwwerk.datumeindegeldigheidoverigbouwwerk ? (
                      <TextFormat type="date" value={overigbouwwerk.datumeindegeldigheidoverigbouwwerk} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{overigbouwwerk.geometrieoverigbouwwerk}</td>
                  <td>{overigbouwwerk.identificatieoverigbouwwerk}</td>
                  <td>{overigbouwwerk.lod0geometrieoverigbouwwerk}</td>
                  <td>{overigbouwwerk.lod1geometrieoverigbouwwerk}</td>
                  <td>{overigbouwwerk.lod2geometrieoverigbouwwerk}</td>
                  <td>{overigbouwwerk.lod3geometrieoverigbouwwerk}</td>
                  <td>{overigbouwwerk.relatievehoogteliggingoverigbouwwerk}</td>
                  <td>{overigbouwwerk.statusoverigbouwwerk}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/overigbouwwerk/${overigbouwwerk.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/overigbouwwerk/${overigbouwwerk.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/overigbouwwerk/${overigbouwwerk.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Overigbouwwerks found</div>
        )}
      </div>
    </div>
  );
};

export default Overigbouwwerk;
