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

import { getEntities } from './overigescheiding.reducer';

export const Overigescheiding = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const overigescheidingList = useAppSelector(state => state.overigescheiding.entities);
  const loading = useAppSelector(state => state.overigescheiding.loading);

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
      <h2 id="overigescheiding-heading" data-cy="OverigescheidingHeading">
        Overigescheidings
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/overigescheiding/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Overigescheiding
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {overigescheidingList && overigescheidingList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('datumbegingeldigheidoverigescheiding')}>
                  Datumbegingeldigheidoverigescheiding{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('datumbegingeldigheidoverigescheiding')} />
                </th>
                <th className="hand" onClick={sort('datumeindegeldigheidoverigescheiding')}>
                  Datumeindegeldigheidoverigescheiding{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('datumeindegeldigheidoverigescheiding')} />
                </th>
                <th className="hand" onClick={sort('geometrieoverigescheiding')}>
                  Geometrieoverigescheiding <FontAwesomeIcon icon={getSortIconByFieldName('geometrieoverigescheiding')} />
                </th>
                <th className="hand" onClick={sort('identificatieoverigescheiding')}>
                  Identificatieoverigescheiding <FontAwesomeIcon icon={getSortIconByFieldName('identificatieoverigescheiding')} />
                </th>
                <th className="hand" onClick={sort('lod0geometrieoverigescheiding')}>
                  Lod 0 Geometrieoverigescheiding <FontAwesomeIcon icon={getSortIconByFieldName('lod0geometrieoverigescheiding')} />
                </th>
                <th className="hand" onClick={sort('lod1geometrieoverigescheiding')}>
                  Lod 1 Geometrieoverigescheiding <FontAwesomeIcon icon={getSortIconByFieldName('lod1geometrieoverigescheiding')} />
                </th>
                <th className="hand" onClick={sort('lod2geometrieoverigescheiding')}>
                  Lod 2 Geometrieoverigescheiding <FontAwesomeIcon icon={getSortIconByFieldName('lod2geometrieoverigescheiding')} />
                </th>
                <th className="hand" onClick={sort('lod3geometrieoverigescheiding')}>
                  Lod 3 Geometrieoverigescheiding <FontAwesomeIcon icon={getSortIconByFieldName('lod3geometrieoverigescheiding')} />
                </th>
                <th className="hand" onClick={sort('relatievehoogteliggingoverigescheiding')}>
                  Relatievehoogteliggingoverigescheiding{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('relatievehoogteliggingoverigescheiding')} />
                </th>
                <th className="hand" onClick={sort('statusoverigescheiding')}>
                  Statusoverigescheiding <FontAwesomeIcon icon={getSortIconByFieldName('statusoverigescheiding')} />
                </th>
                <th className="hand" onClick={sort('typeoverigescheiding')}>
                  Typeoverigescheiding <FontAwesomeIcon icon={getSortIconByFieldName('typeoverigescheiding')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {overigescheidingList.map((overigescheiding, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/overigescheiding/${overigescheiding.id}`} color="link" size="sm">
                      {overigescheiding.id}
                    </Button>
                  </td>
                  <td>
                    {overigescheiding.datumbegingeldigheidoverigescheiding ? (
                      <TextFormat
                        type="date"
                        value={overigescheiding.datumbegingeldigheidoverigescheiding}
                        format={APP_LOCAL_DATE_FORMAT}
                      />
                    ) : null}
                  </td>
                  <td>
                    {overigescheiding.datumeindegeldigheidoverigescheiding ? (
                      <TextFormat
                        type="date"
                        value={overigescheiding.datumeindegeldigheidoverigescheiding}
                        format={APP_LOCAL_DATE_FORMAT}
                      />
                    ) : null}
                  </td>
                  <td>{overigescheiding.geometrieoverigescheiding}</td>
                  <td>{overigescheiding.identificatieoverigescheiding}</td>
                  <td>{overigescheiding.lod0geometrieoverigescheiding}</td>
                  <td>{overigescheiding.lod1geometrieoverigescheiding}</td>
                  <td>{overigescheiding.lod2geometrieoverigescheiding}</td>
                  <td>{overigescheiding.lod3geometrieoverigescheiding}</td>
                  <td>{overigescheiding.relatievehoogteliggingoverigescheiding}</td>
                  <td>{overigescheiding.statusoverigescheiding}</td>
                  <td>{overigescheiding.typeoverigescheiding}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/overigescheiding/${overigescheiding.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/overigescheiding/${overigescheiding.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/overigescheiding/${overigescheiding.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Overigescheidings found</div>
        )}
      </div>
    </div>
  );
};

export default Overigescheiding;
