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

import { getEntities } from './plan.reducer';

export const Plan = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const planList = useAppSelector(state => state.plan.entities);
  const loading = useAppSelector(state => state.plan.loading);

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
      <h2 id="plan-heading" data-cy="PlanHeading">
        Plans
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/plan/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Plan
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {planList && planList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('zeventigprocentverkocht')}>
                  Zeventigprocentverkocht <FontAwesomeIcon icon={getSortIconByFieldName('zeventigprocentverkocht')} />
                </th>
                <th className="hand" onClick={sort('aardgasloos')}>
                  Aardgasloos <FontAwesomeIcon icon={getSortIconByFieldName('aardgasloos')} />
                </th>
                <th className="hand" onClick={sort('bestemminggoedgekeurd')}>
                  Bestemminggoedgekeurd <FontAwesomeIcon icon={getSortIconByFieldName('bestemminggoedgekeurd')} />
                </th>
                <th className="hand" onClick={sort('eersteoplevering')}>
                  Eersteoplevering <FontAwesomeIcon icon={getSortIconByFieldName('eersteoplevering')} />
                </th>
                <th className="hand" onClick={sort('eigendomgemeente')}>
                  Eigendomgemeente <FontAwesomeIcon icon={getSortIconByFieldName('eigendomgemeente')} />
                </th>
                <th className="hand" onClick={sort('gebiedstransformatie')}>
                  Gebiedstransformatie <FontAwesomeIcon icon={getSortIconByFieldName('gebiedstransformatie')} />
                </th>
                <th className="hand" onClick={sort('intentie')}>
                  Intentie <FontAwesomeIcon icon={getSortIconByFieldName('intentie')} />
                </th>
                <th className="hand" onClick={sort('laatsteoplevering')}>
                  Laatsteoplevering <FontAwesomeIcon icon={getSortIconByFieldName('laatsteoplevering')} />
                </th>
                <th className="hand" onClick={sort('naam')}>
                  Naam <FontAwesomeIcon icon={getSortIconByFieldName('naam')} />
                </th>
                <th className="hand" onClick={sort('nummer')}>
                  Nummer <FontAwesomeIcon icon={getSortIconByFieldName('nummer')} />
                </th>
                <th className="hand" onClick={sort('onherroepelijk')}>
                  Onherroepelijk <FontAwesomeIcon icon={getSortIconByFieldName('onherroepelijk')} />
                </th>
                <th className="hand" onClick={sort('percelen')}>
                  Percelen <FontAwesomeIcon icon={getSortIconByFieldName('percelen')} />
                </th>
                <th className="hand" onClick={sort('startbouw')}>
                  Startbouw <FontAwesomeIcon icon={getSortIconByFieldName('startbouw')} />
                </th>
                <th className="hand" onClick={sort('startverkoop')}>
                  Startverkoop <FontAwesomeIcon icon={getSortIconByFieldName('startverkoop')} />
                </th>
                <th>
                  Binnenprogramma Programma <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Isprojectleidervan Projectleider <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Heeft Projectontwikkelaar <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {planList.map((plan, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/plan/${plan.id}`} color="link" size="sm">
                      {plan.id}
                    </Button>
                  </td>
                  <td>{plan.zeventigprocentverkocht ? 'true' : 'false'}</td>
                  <td>{plan.aardgasloos ? 'true' : 'false'}</td>
                  <td>{plan.bestemminggoedgekeurd ? 'true' : 'false'}</td>
                  <td>
                    {plan.eersteoplevering ? <TextFormat type="date" value={plan.eersteoplevering} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>{plan.eigendomgemeente ? 'true' : 'false'}</td>
                  <td>{plan.gebiedstransformatie ? 'true' : 'false'}</td>
                  <td>{plan.intentie ? 'true' : 'false'}</td>
                  <td>
                    {plan.laatsteoplevering ? (
                      <TextFormat type="date" value={plan.laatsteoplevering} format={APP_LOCAL_DATE_FORMAT} />
                    ) : null}
                  </td>
                  <td>{plan.naam}</td>
                  <td>{plan.nummer}</td>
                  <td>{plan.onherroepelijk ? 'true' : 'false'}</td>
                  <td>{plan.percelen}</td>
                  <td>{plan.startbouw ? <TextFormat type="date" value={plan.startbouw} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{plan.startverkoop ? <TextFormat type="date" value={plan.startverkoop} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>
                    {plan.binnenprogrammaProgramma ? (
                      <Link to={`/programma/${plan.binnenprogrammaProgramma.id}`}>{plan.binnenprogrammaProgramma.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {plan.isprojectleidervanProjectleider ? (
                      <Link to={`/projectleider/${plan.isprojectleidervanProjectleider.id}`}>
                        {plan.isprojectleidervanProjectleider.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {plan.heeftProjectontwikkelaars
                      ? plan.heeftProjectontwikkelaars.map((val, j) => (
                          <span key={j}>
                            <Link to={`/projectontwikkelaar/${val.id}`}>{val.id}</Link>
                            {j === plan.heeftProjectontwikkelaars.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/plan/${plan.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/plan/${plan.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/plan/${plan.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Plans found</div>
        )}
      </div>
    </div>
  );
};

export default Plan;
