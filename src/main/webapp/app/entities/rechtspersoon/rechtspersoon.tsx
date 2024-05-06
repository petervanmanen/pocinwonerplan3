import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './rechtspersoon.reducer';

export const Rechtspersoon = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const rechtspersoonList = useAppSelector(state => state.rechtspersoon.entities);
  const loading = useAppSelector(state => state.rechtspersoon.loading);

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
      <h2 id="rechtspersoon-heading" data-cy="RechtspersoonHeading">
        Rechtspersoons
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/rechtspersoon/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Rechtspersoon
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {rechtspersoonList && rechtspersoonList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('adresbinnenland')}>
                  Adresbinnenland <FontAwesomeIcon icon={getSortIconByFieldName('adresbinnenland')} />
                </th>
                <th className="hand" onClick={sort('adresbuitenland')}>
                  Adresbuitenland <FontAwesomeIcon icon={getSortIconByFieldName('adresbuitenland')} />
                </th>
                <th className="hand" onClick={sort('adrescorrespondentie')}>
                  Adrescorrespondentie <FontAwesomeIcon icon={getSortIconByFieldName('adrescorrespondentie')} />
                </th>
                <th className="hand" onClick={sort('emailadres')}>
                  Emailadres <FontAwesomeIcon icon={getSortIconByFieldName('emailadres')} />
                </th>
                <th className="hand" onClick={sort('faxnummer')}>
                  Faxnummer <FontAwesomeIcon icon={getSortIconByFieldName('faxnummer')} />
                </th>
                <th className="hand" onClick={sort('identificatie')}>
                  Identificatie <FontAwesomeIcon icon={getSortIconByFieldName('identificatie')} />
                </th>
                <th className="hand" onClick={sort('kvknummer')}>
                  Kvknummer <FontAwesomeIcon icon={getSortIconByFieldName('kvknummer')} />
                </th>
                <th className="hand" onClick={sort('naam')}>
                  Naam <FontAwesomeIcon icon={getSortIconByFieldName('naam')} />
                </th>
                <th className="hand" onClick={sort('rechtsvorm')}>
                  Rechtsvorm <FontAwesomeIcon icon={getSortIconByFieldName('rechtsvorm')} />
                </th>
                <th className="hand" onClick={sort('rekeningnummer')}>
                  Rekeningnummer <FontAwesomeIcon icon={getSortIconByFieldName('rekeningnummer')} />
                </th>
                <th className="hand" onClick={sort('telefoonnummer')}>
                  Telefoonnummer <FontAwesomeIcon icon={getSortIconByFieldName('telefoonnummer')} />
                </th>
                <th>
                  Betrokkenen Kadastralemutatie <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {rechtspersoonList.map((rechtspersoon, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/rechtspersoon/${rechtspersoon.id}`} color="link" size="sm">
                      {rechtspersoon.id}
                    </Button>
                  </td>
                  <td>{rechtspersoon.adresbinnenland}</td>
                  <td>{rechtspersoon.adresbuitenland}</td>
                  <td>{rechtspersoon.adrescorrespondentie}</td>
                  <td>{rechtspersoon.emailadres}</td>
                  <td>{rechtspersoon.faxnummer}</td>
                  <td>{rechtspersoon.identificatie}</td>
                  <td>{rechtspersoon.kvknummer}</td>
                  <td>{rechtspersoon.naam}</td>
                  <td>{rechtspersoon.rechtsvorm}</td>
                  <td>{rechtspersoon.rekeningnummer}</td>
                  <td>{rechtspersoon.telefoonnummer}</td>
                  <td>
                    {rechtspersoon.betrokkenenKadastralemutaties
                      ? rechtspersoon.betrokkenenKadastralemutaties.map((val, j) => (
                          <span key={j}>
                            <Link to={`/kadastralemutatie/${val.id}`}>{val.id}</Link>
                            {j === rechtspersoon.betrokkenenKadastralemutaties.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/rechtspersoon/${rechtspersoon.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/rechtspersoon/${rechtspersoon.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/rechtspersoon/${rechtspersoon.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Rechtspersoons found</div>
        )}
      </div>
    </div>
  );
};

export default Rechtspersoon;
