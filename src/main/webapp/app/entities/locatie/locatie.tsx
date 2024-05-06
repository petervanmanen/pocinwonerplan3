import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './locatie.reducer';

export const Locatie = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const locatieList = useAppSelector(state => state.locatie.entities);
  const loading = useAppSelector(state => state.locatie.loading);

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
      <h2 id="locatie-heading" data-cy="LocatieHeading">
        Locaties
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/locatie/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Locatie
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {locatieList && locatieList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('adres')}>
                  Adres <FontAwesomeIcon icon={getSortIconByFieldName('adres')} />
                </th>
                <th>
                  Heeftlocatie Put <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Wordtbegrensddoor Project <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Betreft Verzoek <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Werkingsgebied Regeltekst <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Isverbondenmet Activiteit <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Verwijstnaar Gebiedsaanwijzing <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Geldtvoor Normwaarde <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {locatieList.map((locatie, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/locatie/${locatie.id}`} color="link" size="sm">
                      {locatie.id}
                    </Button>
                  </td>
                  <td>{locatie.adres}</td>
                  <td>
                    {locatie.heeftlocatiePuts
                      ? locatie.heeftlocatiePuts.map((val, j) => (
                          <span key={j}>
                            <Link to={`/put/${val.id}`}>{val.id}</Link>
                            {j === locatie.heeftlocatiePuts.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {locatie.wordtbegrensddoorProjects
                      ? locatie.wordtbegrensddoorProjects.map((val, j) => (
                          <span key={j}>
                            <Link to={`/project/${val.id}`}>{val.id}</Link>
                            {j === locatie.wordtbegrensddoorProjects.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {locatie.betreftVerzoeks
                      ? locatie.betreftVerzoeks.map((val, j) => (
                          <span key={j}>
                            <Link to={`/verzoek/${val.id}`}>{val.id}</Link>
                            {j === locatie.betreftVerzoeks.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {locatie.werkingsgebiedRegelteksts
                      ? locatie.werkingsgebiedRegelteksts.map((val, j) => (
                          <span key={j}>
                            <Link to={`/regeltekst/${val.id}`}>{val.id}</Link>
                            {j === locatie.werkingsgebiedRegelteksts.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {locatie.isverbondenmetActiviteits
                      ? locatie.isverbondenmetActiviteits.map((val, j) => (
                          <span key={j}>
                            <Link to={`/activiteit/${val.id}`}>{val.id}</Link>
                            {j === locatie.isverbondenmetActiviteits.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {locatie.verwijstnaarGebiedsaanwijzings
                      ? locatie.verwijstnaarGebiedsaanwijzings.map((val, j) => (
                          <span key={j}>
                            <Link to={`/gebiedsaanwijzing/${val.id}`}>{val.id}</Link>
                            {j === locatie.verwijstnaarGebiedsaanwijzings.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {locatie.geldtvoorNormwaardes
                      ? locatie.geldtvoorNormwaardes.map((val, j) => (
                          <span key={j}>
                            <Link to={`/normwaarde/${val.id}`}>{val.id}</Link>
                            {j === locatie.geldtvoorNormwaardes.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/locatie/${locatie.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/locatie/${locatie.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/locatie/${locatie.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Locaties found</div>
        )}
      </div>
    </div>
  );
};

export default Locatie;
