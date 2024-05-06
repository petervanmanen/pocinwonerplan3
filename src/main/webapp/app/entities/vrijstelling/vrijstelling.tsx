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

import { getEntities } from './vrijstelling.reducer';

export const Vrijstelling = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const vrijstellingList = useAppSelector(state => state.vrijstelling.entities);
  const loading = useAppSelector(state => state.vrijstelling.loading);

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
      <h2 id="vrijstelling-heading" data-cy="VrijstellingHeading">
        Vrijstellings
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/vrijstelling/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Vrijstelling
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {vrijstellingList && vrijstellingList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('aanvraagtoegekend')}>
                  Aanvraagtoegekend <FontAwesomeIcon icon={getSortIconByFieldName('aanvraagtoegekend')} />
                </th>
                <th className="hand" onClick={sort('buitenlandseschoollocatie')}>
                  Buitenlandseschoollocatie <FontAwesomeIcon icon={getSortIconByFieldName('buitenlandseschoollocatie')} />
                </th>
                <th className="hand" onClick={sort('datumeinde')}>
                  Datumeinde <FontAwesomeIcon icon={getSortIconByFieldName('datumeinde')} />
                </th>
                <th className="hand" onClick={sort('datumstart')}>
                  Datumstart <FontAwesomeIcon icon={getSortIconByFieldName('datumstart')} />
                </th>
                <th className="hand" onClick={sort('verzuimsoort')}>
                  Verzuimsoort <FontAwesomeIcon icon={getSortIconByFieldName('verzuimsoort')} />
                </th>
                <th>
                  Heeft School <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Heeft Leerling <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {vrijstellingList.map((vrijstelling, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/vrijstelling/${vrijstelling.id}`} color="link" size="sm">
                      {vrijstelling.id}
                    </Button>
                  </td>
                  <td>{vrijstelling.aanvraagtoegekend ? 'true' : 'false'}</td>
                  <td>{vrijstelling.buitenlandseschoollocatie}</td>
                  <td>
                    {vrijstelling.datumeinde ? (
                      <TextFormat type="date" value={vrijstelling.datumeinde} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {vrijstelling.datumstart ? (
                      <TextFormat type="date" value={vrijstelling.datumstart} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{vrijstelling.verzuimsoort}</td>
                  <td>
                    {vrijstelling.heeftSchool ? (
                      <Link to={`/school/${vrijstelling.heeftSchool.id}`}>{vrijstelling.heeftSchool.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {vrijstelling.heeftLeerling ? (
                      <Link to={`/leerling/${vrijstelling.heeftLeerling.id}`}>{vrijstelling.heeftLeerling.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/vrijstelling/${vrijstelling.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/vrijstelling/${vrijstelling.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/vrijstelling/${vrijstelling.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Vrijstellings found</div>
        )}
      </div>
    </div>
  );
};

export default Vrijstelling;
