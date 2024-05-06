import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './sportvereniging.reducer';

export const Sportvereniging = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const sportverenigingList = useAppSelector(state => state.sportvereniging.entities);
  const loading = useAppSelector(state => state.sportvereniging.loading);

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
      <h2 id="sportvereniging-heading" data-cy="SportverenigingHeading">
        Sportverenigings
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/sportvereniging/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Sportvereniging
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {sportverenigingList && sportverenigingList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('aantalnormteams')}>
                  Aantalnormteams <FontAwesomeIcon icon={getSortIconByFieldName('aantalnormteams')} />
                </th>
                <th className="hand" onClick={sort('adres')}>
                  Adres <FontAwesomeIcon icon={getSortIconByFieldName('adres')} />
                </th>
                <th className="hand" onClick={sort('binnensport')}>
                  Binnensport <FontAwesomeIcon icon={getSortIconByFieldName('binnensport')} />
                </th>
                <th className="hand" onClick={sort('buitensport')}>
                  Buitensport <FontAwesomeIcon icon={getSortIconByFieldName('buitensport')} />
                </th>
                <th className="hand" onClick={sort('email')}>
                  Email <FontAwesomeIcon icon={getSortIconByFieldName('email')} />
                </th>
                <th className="hand" onClick={sort('ledenaantal')}>
                  Ledenaantal <FontAwesomeIcon icon={getSortIconByFieldName('ledenaantal')} />
                </th>
                <th className="hand" onClick={sort('naam')}>
                  Naam <FontAwesomeIcon icon={getSortIconByFieldName('naam')} />
                </th>
                <th className="hand" onClick={sort('typesport')}>
                  Typesport <FontAwesomeIcon icon={getSortIconByFieldName('typesport')} />
                </th>
                <th>
                  Oefentuit Sport <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Gebruikt Sportlocatie <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {sportverenigingList.map((sportvereniging, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/sportvereniging/${sportvereniging.id}`} color="link" size="sm">
                      {sportvereniging.id}
                    </Button>
                  </td>
                  <td>{sportvereniging.aantalnormteams}</td>
                  <td>{sportvereniging.adres}</td>
                  <td>{sportvereniging.binnensport ? 'true' : 'false'}</td>
                  <td>{sportvereniging.buitensport ? 'true' : 'false'}</td>
                  <td>{sportvereniging.email}</td>
                  <td>{sportvereniging.ledenaantal}</td>
                  <td>{sportvereniging.naam}</td>
                  <td>{sportvereniging.typesport}</td>
                  <td>
                    {sportvereniging.oefentuitSports
                      ? sportvereniging.oefentuitSports.map((val, j) => (
                          <span key={j}>
                            <Link to={`/sport/${val.id}`}>{val.id}</Link>
                            {j === sportvereniging.oefentuitSports.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {sportvereniging.gebruiktSportlocaties
                      ? sportvereniging.gebruiktSportlocaties.map((val, j) => (
                          <span key={j}>
                            <Link to={`/sportlocatie/${val.id}`}>{val.id}</Link>
                            {j === sportvereniging.gebruiktSportlocaties.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/sportvereniging/${sportvereniging.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/sportvereniging/${sportvereniging.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/sportvereniging/${sportvereniging.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Sportverenigings found</div>
        )}
      </div>
    </div>
  );
};

export default Sportvereniging;
