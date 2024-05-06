import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './activiteit.reducer';

export const Activiteit = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const activiteitList = useAppSelector(state => state.activiteit.entities);
  const loading = useAppSelector(state => state.activiteit.loading);

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
      <h2 id="activiteit-heading" data-cy="ActiviteitHeading">
        Activiteits
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/activiteit/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Activiteit
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {activiteitList && activiteitList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('omschrijving')}>
                  Omschrijving <FontAwesomeIcon icon={getSortIconByFieldName('omschrijving')} />
                </th>
                <th>
                  Gerelateerdeactiviteit Activiteit <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Bovenliggendeactiviteit Activiteit <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Heeft Rondleiding <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Vansoort Activiteitsoort <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Isverbondenmet Locatie <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Bestaatuit Activiteit 2 <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Bestaatuit Programma <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Betreft Verzoek <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {activiteitList.map((activiteit, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/activiteit/${activiteit.id}`} color="link" size="sm">
                      {activiteit.id}
                    </Button>
                  </td>
                  <td>{activiteit.omschrijving}</td>
                  <td>
                    {activiteit.gerelateerdeactiviteitActiviteit ? (
                      <Link to={`/activiteit/${activiteit.gerelateerdeactiviteitActiviteit.id}`}>
                        {activiteit.gerelateerdeactiviteitActiviteit.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {activiteit.bovenliggendeactiviteitActiviteit ? (
                      <Link to={`/activiteit/${activiteit.bovenliggendeactiviteitActiviteit.id}`}>
                        {activiteit.bovenliggendeactiviteitActiviteit.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {activiteit.heeftRondleiding ? (
                      <Link to={`/rondleiding/${activiteit.heeftRondleiding.id}`}>{activiteit.heeftRondleiding.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {activiteit.vansoortActiviteitsoort ? (
                      <Link to={`/activiteitsoort/${activiteit.vansoortActiviteitsoort.id}`}>{activiteit.vansoortActiviteitsoort.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {activiteit.isverbondenmetLocaties
                      ? activiteit.isverbondenmetLocaties.map((val, j) => (
                          <span key={j}>
                            <Link to={`/locatie/${val.id}`}>{val.id}</Link>
                            {j === activiteit.isverbondenmetLocaties.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {activiteit.bestaatuitActiviteit2 ? (
                      <Link to={`/activiteit/${activiteit.bestaatuitActiviteit2.id}`}>{activiteit.bestaatuitActiviteit2.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {activiteit.bestaatuitProgramma ? (
                      <Link to={`/programma/${activiteit.bestaatuitProgramma.id}`}>{activiteit.bestaatuitProgramma.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {activiteit.betreftVerzoeks
                      ? activiteit.betreftVerzoeks.map((val, j) => (
                          <span key={j}>
                            <Link to={`/verzoek/${val.id}`}>{val.id}</Link>
                            {j === activiteit.betreftVerzoeks.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/activiteit/${activiteit.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/activiteit/${activiteit.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/activiteit/${activiteit.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Activiteits found</div>
        )}
      </div>
    </div>
  );
};

export default Activiteit;
