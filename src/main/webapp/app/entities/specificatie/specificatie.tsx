import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './specificatie.reducer';

export const Specificatie = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const specificatieList = useAppSelector(state => state.specificatie.entities);
  const loading = useAppSelector(state => state.specificatie.loading);

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
      <h2 id="specificatie-heading" data-cy="SpecificatieHeading">
        Specificaties
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/specificatie/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Specificatie
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {specificatieList && specificatieList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('antwoord')}>
                  Antwoord <FontAwesomeIcon icon={getSortIconByFieldName('antwoord')} />
                </th>
                <th className="hand" onClick={sort('groepering')}>
                  Groepering <FontAwesomeIcon icon={getSortIconByFieldName('groepering')} />
                </th>
                <th className="hand" onClick={sort('publiceerbaar')}>
                  Publiceerbaar <FontAwesomeIcon icon={getSortIconByFieldName('publiceerbaar')} />
                </th>
                <th className="hand" onClick={sort('vraagclassificatie')}>
                  Vraagclassificatie <FontAwesomeIcon icon={getSortIconByFieldName('vraagclassificatie')} />
                </th>
                <th className="hand" onClick={sort('vraagid')}>
                  Vraagid <FontAwesomeIcon icon={getSortIconByFieldName('vraagid')} />
                </th>
                <th className="hand" onClick={sort('vraagreferentie')}>
                  Vraagreferentie <FontAwesomeIcon icon={getSortIconByFieldName('vraagreferentie')} />
                </th>
                <th className="hand" onClick={sort('vraagtekst')}>
                  Vraagtekst <FontAwesomeIcon icon={getSortIconByFieldName('vraagtekst')} />
                </th>
                <th>
                  Gedefinieerddoor Projectactiviteit <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Bevat Verzoek <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {specificatieList.map((specificatie, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/specificatie/${specificatie.id}`} color="link" size="sm">
                      {specificatie.id}
                    </Button>
                  </td>
                  <td>{specificatie.antwoord}</td>
                  <td>{specificatie.groepering}</td>
                  <td>{specificatie.publiceerbaar}</td>
                  <td>{specificatie.vraagclassificatie}</td>
                  <td>{specificatie.vraagid}</td>
                  <td>{specificatie.vraagreferentie}</td>
                  <td>{specificatie.vraagtekst}</td>
                  <td>
                    {specificatie.gedefinieerddoorProjectactiviteit ? (
                      <Link to={`/projectactiviteit/${specificatie.gedefinieerddoorProjectactiviteit.id}`}>
                        {specificatie.gedefinieerddoorProjectactiviteit.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {specificatie.bevatVerzoek ? (
                      <Link to={`/verzoek/${specificatie.bevatVerzoek.id}`}>{specificatie.bevatVerzoek.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/specificatie/${specificatie.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/specificatie/${specificatie.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/specificatie/${specificatie.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Specificaties found</div>
        )}
      </div>
    </div>
  );
};

export default Specificatie;
