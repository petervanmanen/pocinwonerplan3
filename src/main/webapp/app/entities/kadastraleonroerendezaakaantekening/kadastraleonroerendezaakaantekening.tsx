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

import { getEntities } from './kadastraleonroerendezaakaantekening.reducer';

export const Kadastraleonroerendezaakaantekening = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const kadastraleonroerendezaakaantekeningList = useAppSelector(state => state.kadastraleonroerendezaakaantekening.entities);
  const loading = useAppSelector(state => state.kadastraleonroerendezaakaantekening.loading);

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
      <h2 id="kadastraleonroerendezaakaantekening-heading" data-cy="KadastraleonroerendezaakaantekeningHeading">
        Kadastraleonroerendezaakaantekenings
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/kadastraleonroerendezaakaantekening/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Kadastraleonroerendezaakaantekening
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {kadastraleonroerendezaakaantekeningList && kadastraleonroerendezaakaantekeningList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('aardaantekeningkadastraalobject')}>
                  Aardaantekeningkadastraalobject <FontAwesomeIcon icon={getSortIconByFieldName('aardaantekeningkadastraalobject')} />
                </th>
                <th className="hand" onClick={sort('beschrijvingaantekeningkadastraalobject')}>
                  Beschrijvingaantekeningkadastraalobject{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('beschrijvingaantekeningkadastraalobject')} />
                </th>
                <th className="hand" onClick={sort('datumbeginaantekeningkadastraalobject')}>
                  Datumbeginaantekeningkadastraalobject{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('datumbeginaantekeningkadastraalobject')} />
                </th>
                <th className="hand" onClick={sort('datumeindeaantekeningkadastraalobject')}>
                  Datumeindeaantekeningkadastraalobject{' '}
                  <FontAwesomeIcon icon={getSortIconByFieldName('datumeindeaantekeningkadastraalobject')} />
                </th>
                <th className="hand" onClick={sort('kadasteridentificatieaantekening')}>
                  Kadasteridentificatieaantekening <FontAwesomeIcon icon={getSortIconByFieldName('kadasteridentificatieaantekening')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {kadastraleonroerendezaakaantekeningList.map((kadastraleonroerendezaakaantekening, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button
                      tag={Link}
                      to={`/kadastraleonroerendezaakaantekening/${kadastraleonroerendezaakaantekening.id}`}
                      color="link"
                      size="sm"
                    >
                      {kadastraleonroerendezaakaantekening.id}
                    </Button>
                  </td>
                  <td>{kadastraleonroerendezaakaantekening.aardaantekeningkadastraalobject}</td>
                  <td>{kadastraleonroerendezaakaantekening.beschrijvingaantekeningkadastraalobject}</td>
                  <td>
                    {kadastraleonroerendezaakaantekening.datumbeginaantekeningkadastraalobject ? (
                      <TextFormat
                        type="date"
                        value={kadastraleonroerendezaakaantekening.datumbeginaantekeningkadastraalobject}
                        format={APP_LOCAL_DATE_FORMAT}
                      />
                    ) : null}
                  </td>
                  <td>
                    {kadastraleonroerendezaakaantekening.datumeindeaantekeningkadastraalobject ? (
                      <TextFormat
                        type="date"
                        value={kadastraleonroerendezaakaantekening.datumeindeaantekeningkadastraalobject}
                        format={APP_LOCAL_DATE_FORMAT}
                      />
                    ) : null}
                  </td>
                  <td>{kadastraleonroerendezaakaantekening.kadasteridentificatieaantekening}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/kadastraleonroerendezaakaantekening/${kadastraleonroerendezaakaantekening.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/kadastraleonroerendezaakaantekening/${kadastraleonroerendezaakaantekening.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() =>
                          (window.location.href = `/kadastraleonroerendezaakaantekening/${kadastraleonroerendezaakaantekening.id}/delete`)
                        }
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
          !loading && <div className="alert alert-warning">No Kadastraleonroerendezaakaantekenings found</div>
        )}
      </div>
    </div>
  );
};

export default Kadastraleonroerendezaakaantekening;
