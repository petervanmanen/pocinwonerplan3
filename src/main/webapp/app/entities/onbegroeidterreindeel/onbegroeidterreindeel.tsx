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

import { getEntities } from './onbegroeidterreindeel.reducer';

export const Onbegroeidterreindeel = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const onbegroeidterreindeelList = useAppSelector(state => state.onbegroeidterreindeel.entities);
  const loading = useAppSelector(state => state.onbegroeidterreindeel.loading);

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
      <h2 id="onbegroeidterreindeel-heading" data-cy="OnbegroeidterreindeelHeading">
        Onbegroeidterreindeels
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/onbegroeidterreindeel/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Onbegroeidterreindeel
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {onbegroeidterreindeelList && onbegroeidterreindeelList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('datumbegingeldigheidonbegroeidterreindeel')}>
                  Datumbegingeldigheidonbegroeidterreindeel{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('datumbegingeldigheidonbegroeidterreindeel')} />
                </th>
                <th className="hand" onClick={sort('datumeindegeldigheidonbegroeidterreindeel')}>
                  Datumeindegeldigheidonbegroeidterreindeel{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('datumeindegeldigheidonbegroeidterreindeel')} />
                </th>
                <th className="hand" onClick={sort('fysiekvoorkomenonbegroeidterreindeel')}>
                  Fysiekvoorkomenonbegroeidterreindeel{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('fysiekvoorkomenonbegroeidterreindeel')} />
                </th>
                <th className="hand" onClick={sort('geometrieonbegroeidterreindeel')}>
                  Geometrieonbegroeidterreindeel <FontAwesomeIcon icon={getSortIconByFieldName('geometrieonbegroeidterreindeel')} />
                </th>
                <th className="hand" onClick={sort('identificatieonbegroeidterreindeel')}>
                  Identificatieonbegroeidterreindeel <FontAwesomeIcon icon={getSortIconByFieldName('identificatieonbegroeidterreindeel')} />
                </th>
                <th className="hand" onClick={sort('kruinlijngeometrieonbegroeidterreindeel')}>
                  Kruinlijngeometrieonbegroeidterreindeel{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('kruinlijngeometrieonbegroeidterreindeel')} />
                </th>
                <th className="hand" onClick={sort('onbegroeidterreindeeloptalud')}>
                  Onbegroeidterreindeeloptalud <FontAwesomeIcon icon={getSortIconByFieldName('onbegroeidterreindeeloptalud')} />
                </th>
                <th className="hand" onClick={sort('plusfysiekvoorkomenonbegroeidterreindeel')}>
                  Plusfysiekvoorkomenonbegroeidterreindeel{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('plusfysiekvoorkomenonbegroeidterreindeel')} />
                </th>
                <th className="hand" onClick={sort('relatievehoogteliggingonbegroeidterreindeel')}>
                  Relatievehoogteliggingonbegroeidterreindeel{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('relatievehoogteliggingonbegroeidterreindeel')} />
                </th>
                <th className="hand" onClick={sort('statusonbegroeidterreindeel')}>
                  Statusonbegroeidterreindeel <FontAwesomeIcon icon={getSortIconByFieldName('statusonbegroeidterreindeel')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {onbegroeidterreindeelList.map((onbegroeidterreindeel, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/onbegroeidterreindeel/${onbegroeidterreindeel.id}`} color="link" size="sm">
                      {onbegroeidterreindeel.id}
                    </Button>
                  </td>
                  <td>
                    {onbegroeidterreindeel.datumbegingeldigheidonbegroeidterreindeel ? (
                      <TextFormat
                        type="date"
                        value={onbegroeidterreindeel.datumbegingeldigheidonbegroeidterreindeel}
                        format={APP_LOCAL_DATE_FORMAT}
                      />
                    ) : null}
                  </td>
                  <td>
                    {onbegroeidterreindeel.datumeindegeldigheidonbegroeidterreindeel ? (
                      <TextFormat
                        type="date"
                        value={onbegroeidterreindeel.datumeindegeldigheidonbegroeidterreindeel}
                        format={APP_LOCAL_DATE_FORMAT}
                      />
                    ) : null}
                  </td>
                  <td>{onbegroeidterreindeel.fysiekvoorkomenonbegroeidterreindeel}</td>
                  <td>{onbegroeidterreindeel.geometrieonbegroeidterreindeel}</td>
                  <td>{onbegroeidterreindeel.identificatieonbegroeidterreindeel}</td>
                  <td>{onbegroeidterreindeel.kruinlijngeometrieonbegroeidterreindeel}</td>
                  <td>{onbegroeidterreindeel.onbegroeidterreindeeloptalud}</td>
                  <td>{onbegroeidterreindeel.plusfysiekvoorkomenonbegroeidterreindeel}</td>
                  <td>{onbegroeidterreindeel.relatievehoogteliggingonbegroeidterreindeel}</td>
                  <td>{onbegroeidterreindeel.statusonbegroeidterreindeel}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/onbegroeidterreindeel/${onbegroeidterreindeel.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/onbegroeidterreindeel/${onbegroeidterreindeel.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/onbegroeidterreindeel/${onbegroeidterreindeel.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Onbegroeidterreindeels found</div>
        )}
      </div>
    </div>
  );
};

export default Onbegroeidterreindeel;
