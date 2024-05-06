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

import { getEntities } from './begroeidterreindeel.reducer';

export const Begroeidterreindeel = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const begroeidterreindeelList = useAppSelector(state => state.begroeidterreindeel.entities);
  const loading = useAppSelector(state => state.begroeidterreindeel.loading);

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
      <h2 id="begroeidterreindeel-heading" data-cy="BegroeidterreindeelHeading">
        Begroeidterreindeels
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/begroeidterreindeel/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Begroeidterreindeel
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {begroeidterreindeelList && begroeidterreindeelList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('begroeidterreindeeloptalud')}>
                  Begroeidterreindeeloptalud <FontAwesomeIcon icon={getSortIconByFieldName('begroeidterreindeeloptalud')} />
                </th>
                <th className="hand" onClick={sort('datumbegingeldigheidbegroeidterreindeel')}>
                  Datumbegingeldigheidbegroeidterreindeel{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('datumbegingeldigheidbegroeidterreindeel')} />
                </th>
                <th className="hand" onClick={sort('datumeindegeldigheidbegroeidterreindeel')}>
                  Datumeindegeldigheidbegroeidterreindeel{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('datumeindegeldigheidbegroeidterreindeel')} />
                </th>
                <th className="hand" onClick={sort('fysiekvoorkomenbegroeidterreindeel')}>
                  Fysiekvoorkomenbegroeidterreindeel <FontAwesomeIcon icon={getSortIconByFieldName('fysiekvoorkomenbegroeidterreindeel')} />
                </th>
                <th className="hand" onClick={sort('geometriebegroeidterreindeel')}>
                  Geometriebegroeidterreindeel <FontAwesomeIcon icon={getSortIconByFieldName('geometriebegroeidterreindeel')} />
                </th>
                <th className="hand" onClick={sort('identificatiebegroeidterreindeel')}>
                  Identificatiebegroeidterreindeel <FontAwesomeIcon icon={getSortIconByFieldName('identificatiebegroeidterreindeel')} />
                </th>
                <th className="hand" onClick={sort('kruinlijngeometriebegroeidterreindeel')}>
                  Kruinlijngeometriebegroeidterreindeel{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('kruinlijngeometriebegroeidterreindeel')} />
                </th>
                <th className="hand" onClick={sort('lod0geometriebegroeidterreindeel')}>
                  Lod 0 Geometriebegroeidterreindeel <FontAwesomeIcon icon={getSortIconByFieldName('lod0geometriebegroeidterreindeel')} />
                </th>
                <th className="hand" onClick={sort('plusfysiekvoorkomenbegroeidterreindeel')}>
                  Plusfysiekvoorkomenbegroeidterreindeel{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('plusfysiekvoorkomenbegroeidterreindeel')} />
                </th>
                <th className="hand" onClick={sort('relatievehoogteliggingbegroeidterreindeel')}>
                  Relatievehoogteliggingbegroeidterreindeel{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('relatievehoogteliggingbegroeidterreindeel')} />
                </th>
                <th className="hand" onClick={sort('statusbegroeidterreindeel')}>
                  Statusbegroeidterreindeel <FontAwesomeIcon icon={getSortIconByFieldName('statusbegroeidterreindeel')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {begroeidterreindeelList.map((begroeidterreindeel, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/begroeidterreindeel/${begroeidterreindeel.id}`} color="link" size="sm">
                      {begroeidterreindeel.id}
                    </Button>
                  </td>
                  <td>{begroeidterreindeel.begroeidterreindeeloptalud}</td>
                  <td>
                    {begroeidterreindeel.datumbegingeldigheidbegroeidterreindeel ? (
                      <TextFormat
                        type="date"
                        value={begroeidterreindeel.datumbegingeldigheidbegroeidterreindeel}
                        format={APP_LOCAL_DATE_FORMAT}
                      />
                    ) : null}
                  </td>
                  <td>
                    {begroeidterreindeel.datumeindegeldigheidbegroeidterreindeel ? (
                      <TextFormat
                        type="date"
                        value={begroeidterreindeel.datumeindegeldigheidbegroeidterreindeel}
                        format={APP_LOCAL_DATE_FORMAT}
                      />
                    ) : null}
                  </td>
                  <td>{begroeidterreindeel.fysiekvoorkomenbegroeidterreindeel}</td>
                  <td>{begroeidterreindeel.geometriebegroeidterreindeel}</td>
                  <td>{begroeidterreindeel.identificatiebegroeidterreindeel}</td>
                  <td>{begroeidterreindeel.kruinlijngeometriebegroeidterreindeel}</td>
                  <td>{begroeidterreindeel.lod0geometriebegroeidterreindeel}</td>
                  <td>{begroeidterreindeel.plusfysiekvoorkomenbegroeidterreindeel}</td>
                  <td>{begroeidterreindeel.relatievehoogteliggingbegroeidterreindeel}</td>
                  <td>{begroeidterreindeel.statusbegroeidterreindeel}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/begroeidterreindeel/${begroeidterreindeel.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/begroeidterreindeel/${begroeidterreindeel.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/begroeidterreindeel/${begroeidterreindeel.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Begroeidterreindeels found</div>
        )}
      </div>
    </div>
  );
};

export default Begroeidterreindeel;
