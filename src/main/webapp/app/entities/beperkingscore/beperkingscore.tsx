import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './beperkingscore.reducer';

export const Beperkingscore = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const beperkingscoreList = useAppSelector(state => state.beperkingscore.entities);
  const loading = useAppSelector(state => state.beperkingscore.loading);

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
      <h2 id="beperkingscore-heading" data-cy="BeperkingscoreHeading">
        Beperkingscores
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/beperkingscore/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Beperkingscore
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {beperkingscoreList && beperkingscoreList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('commentaar')}>
                  Commentaar <FontAwesomeIcon icon={getSortIconByFieldName('commentaar')} />
                </th>
                <th className="hand" onClick={sort('score')}>
                  Score <FontAwesomeIcon icon={getSortIconByFieldName('score')} />
                </th>
                <th className="hand" onClick={sort('wet')}>
                  Wet <FontAwesomeIcon icon={getSortIconByFieldName('wet')} />
                </th>
                <th>
                  Iseen Beperkingscoresoort <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Empty Beperking <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {beperkingscoreList.map((beperkingscore, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/beperkingscore/${beperkingscore.id}`} color="link" size="sm">
                      {beperkingscore.id}
                    </Button>
                  </td>
                  <td>{beperkingscore.commentaar}</td>
                  <td>{beperkingscore.score}</td>
                  <td>{beperkingscore.wet}</td>
                  <td>
                    {beperkingscore.iseenBeperkingscoresoort ? (
                      <Link to={`/beperkingscoresoort/${beperkingscore.iseenBeperkingscoresoort.id}`}>
                        {beperkingscore.iseenBeperkingscoresoort.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {beperkingscore.emptyBeperking ? (
                      <Link to={`/beperking/${beperkingscore.emptyBeperking.id}`}>{beperkingscore.emptyBeperking.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/beperkingscore/${beperkingscore.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/beperkingscore/${beperkingscore.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/beperkingscore/${beperkingscore.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Beperkingscores found</div>
        )}
      </div>
    </div>
  );
};

export default Beperkingscore;
