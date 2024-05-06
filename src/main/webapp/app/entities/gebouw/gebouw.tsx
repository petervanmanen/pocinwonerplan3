import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './gebouw.reducer';

export const Gebouw = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const gebouwList = useAppSelector(state => state.gebouw.entities);
  const loading = useAppSelector(state => state.gebouw.loading);

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
      <h2 id="gebouw-heading" data-cy="GebouwHeading">
        Gebouws
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/gebouw/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Gebouw
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {gebouwList && gebouwList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('aantal')}>
                  Aantal <FontAwesomeIcon icon={getSortIconByFieldName('aantal')} />
                </th>
                <th className="hand" onClick={sort('aantaladressen')}>
                  Aantaladressen <FontAwesomeIcon icon={getSortIconByFieldName('aantaladressen')} />
                </th>
                <th className="hand" onClick={sort('aantalkamers')}>
                  Aantalkamers <FontAwesomeIcon icon={getSortIconByFieldName('aantalkamers')} />
                </th>
                <th className="hand" onClick={sort('aardgasloos')}>
                  Aardgasloos <FontAwesomeIcon icon={getSortIconByFieldName('aardgasloos')} />
                </th>
                <th className="hand" onClick={sort('duurzaam')}>
                  Duurzaam <FontAwesomeIcon icon={getSortIconByFieldName('duurzaam')} />
                </th>
                <th className="hand" onClick={sort('energielabel')}>
                  Energielabel <FontAwesomeIcon icon={getSortIconByFieldName('energielabel')} />
                </th>
                <th className="hand" onClick={sort('natuurinclusief')}>
                  Natuurinclusief <FontAwesomeIcon icon={getSortIconByFieldName('natuurinclusief')} />
                </th>
                <th className="hand" onClick={sort('oppervlakte')}>
                  Oppervlakte <FontAwesomeIcon icon={getSortIconByFieldName('oppervlakte')} />
                </th>
                <th className="hand" onClick={sort('regenwater')}>
                  Regenwater <FontAwesomeIcon icon={getSortIconByFieldName('regenwater')} />
                </th>
                <th>
                  Bestaatuit Plan <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {gebouwList.map((gebouw, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/gebouw/${gebouw.id}`} color="link" size="sm">
                      {gebouw.id}
                    </Button>
                  </td>
                  <td>{gebouw.aantal}</td>
                  <td>{gebouw.aantaladressen}</td>
                  <td>{gebouw.aantalkamers}</td>
                  <td>{gebouw.aardgasloos ? 'true' : 'false'}</td>
                  <td>{gebouw.duurzaam ? 'true' : 'false'}</td>
                  <td>{gebouw.energielabel}</td>
                  <td>{gebouw.natuurinclusief ? 'true' : 'false'}</td>
                  <td>{gebouw.oppervlakte}</td>
                  <td>{gebouw.regenwater ? 'true' : 'false'}</td>
                  <td>{gebouw.bestaatuitPlan ? <Link to={`/plan/${gebouw.bestaatuitPlan.id}`}>{gebouw.bestaatuitPlan.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/gebouw/${gebouw.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/gebouw/${gebouw.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/gebouw/${gebouw.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Gebouws found</div>
        )}
      </div>
    </div>
  );
};

export default Gebouw;
