import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './traject.reducer';

export const Traject = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const trajectList = useAppSelector(state => state.traject.entities);
  const loading = useAppSelector(state => state.traject.loading);

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
      <h2 id="traject-heading" data-cy="TrajectHeading">
        Trajects
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/traject/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Traject
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {trajectList && trajectList.length > 0 ? (
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
                <th className="hand" onClick={sort('datumtoekenning')}>
                  Datumtoekenning <FontAwesomeIcon icon={getSortIconByFieldName('datumtoekenning')} />
                </th>
                <th className="hand" onClick={sort('naam')}>
                  Naam <FontAwesomeIcon icon={getSortIconByFieldName('naam')} />
                </th>
                <th className="hand" onClick={sort('omschrijving')}>
                  Omschrijving <FontAwesomeIcon icon={getSortIconByFieldName('omschrijving')} />
                </th>
                <th className="hand" onClick={sort('resultaat')}>
                  Resultaat <FontAwesomeIcon icon={getSortIconByFieldName('resultaat')} />
                </th>
                <th>
                  Heeftresultaat Resultaat <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Heeftuitstroomreden Uitstroomreden <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Istrajectsoort Trajectsoort <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Heeftparticipatietraject Client <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Heefttraject Client <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {trajectList.map((traject, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/traject/${traject.id}`} color="link" size="sm">
                      {traject.id}
                    </Button>
                  </td>
                  <td>{traject.datumeinde}</td>
                  <td>{traject.datumstart}</td>
                  <td>{traject.datumtoekenning}</td>
                  <td>{traject.naam}</td>
                  <td>{traject.omschrijving}</td>
                  <td>{traject.resultaat}</td>
                  <td>
                    {traject.heeftresultaatResultaat ? (
                      <Link to={`/resultaat/${traject.heeftresultaatResultaat.id}`}>{traject.heeftresultaatResultaat.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {traject.heeftuitstroomredenUitstroomreden ? (
                      <Link to={`/uitstroomreden/${traject.heeftuitstroomredenUitstroomreden.id}`}>
                        {traject.heeftuitstroomredenUitstroomreden.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {traject.istrajectsoortTrajectsoort ? (
                      <Link to={`/trajectsoort/${traject.istrajectsoortTrajectsoort.id}`}>{traject.istrajectsoortTrajectsoort.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {traject.heeftparticipatietrajectClient ? (
                      <Link to={`/client/${traject.heeftparticipatietrajectClient.id}`}>{traject.heeftparticipatietrajectClient.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {traject.heefttrajectClient ? (
                      <Link to={`/client/${traject.heefttrajectClient.id}`}>{traject.heefttrajectClient.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/traject/${traject.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/traject/${traject.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/traject/${traject.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Trajects found</div>
        )}
      </div>
    </div>
  );
};

export default Traject;
