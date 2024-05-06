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

import { getEntities } from './tentoonstelling.reducer';

export const Tentoonstelling = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const tentoonstellingList = useAppSelector(state => state.tentoonstelling.entities);
  const loading = useAppSelector(state => state.tentoonstelling.loading);

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
      <h2 id="tentoonstelling-heading" data-cy="TentoonstellingHeading">
        Tentoonstellings
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/tentoonstelling/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Tentoonstelling
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {tentoonstellingList && tentoonstellingList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('datumeinde')}>
                  Datumeinde <FontAwesomeIcon icon={getSortIconByFieldName('datumeinde')} />
                </th>
                <th className="hand" onClick={sort('datumstart')}>
                  Datumstart <FontAwesomeIcon icon={getSortIconByFieldName('datumstart')} />
                </th>
                <th className="hand" onClick={sort('omschrijving')}>
                  Omschrijving <FontAwesomeIcon icon={getSortIconByFieldName('omschrijving')} />
                </th>
                <th className="hand" onClick={sort('subtitel')}>
                  Subtitel <FontAwesomeIcon icon={getSortIconByFieldName('subtitel')} />
                </th>
                <th className="hand" onClick={sort('titel')}>
                  Titel <FontAwesomeIcon icon={getSortIconByFieldName('titel')} />
                </th>
                <th>
                  Isbedoeldvoor Bruikleen <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Onderdeel Museumobject <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Steltsamen Samensteller <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {tentoonstellingList.map((tentoonstelling, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/tentoonstelling/${tentoonstelling.id}`} color="link" size="sm">
                      {tentoonstelling.id}
                    </Button>
                  </td>
                  <td>
                    {tentoonstelling.datumeinde ? (
                      <TextFormat type="date" value={tentoonstelling.datumeinde} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>
                    {tentoonstelling.datumstart ? (
                      <TextFormat type="date" value={tentoonstelling.datumstart} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{tentoonstelling.omschrijving}</td>
                  <td>{tentoonstelling.subtitel}</td>
                  <td>{tentoonstelling.titel}</td>
                  <td>
                    {tentoonstelling.isbedoeldvoorBruikleens
                      ? tentoonstelling.isbedoeldvoorBruikleens.map((val, j) => (
                          <span key={j}>
                            <Link to={`/bruikleen/${val.id}`}>{val.id}</Link>
                            {j === tentoonstelling.isbedoeldvoorBruikleens.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {tentoonstelling.onderdeelMuseumobjects
                      ? tentoonstelling.onderdeelMuseumobjects.map((val, j) => (
                          <span key={j}>
                            <Link to={`/museumobject/${val.id}`}>{val.id}</Link>
                            {j === tentoonstelling.onderdeelMuseumobjects.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {tentoonstelling.steltsamenSamenstellers
                      ? tentoonstelling.steltsamenSamenstellers.map((val, j) => (
                          <span key={j}>
                            <Link to={`/samensteller/${val.id}`}>{val.id}</Link>
                            {j === tentoonstelling.steltsamenSamenstellers.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/tentoonstelling/${tentoonstelling.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/tentoonstelling/${tentoonstelling.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/tentoonstelling/${tentoonstelling.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Tentoonstellings found</div>
        )}
      </div>
    </div>
  );
};

export default Tentoonstelling;
