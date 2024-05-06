import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './hoofdrekening.reducer';

export const Hoofdrekening = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const hoofdrekeningList = useAppSelector(state => state.hoofdrekening.entities);
  const loading = useAppSelector(state => state.hoofdrekening.loading);

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
      <h2 id="hoofdrekening-heading" data-cy="HoofdrekeningHeading">
        Hoofdrekenings
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/hoofdrekening/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Hoofdrekening
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {hoofdrekeningList && hoofdrekeningList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('naam')}>
                  Naam <FontAwesomeIcon icon={getSortIconByFieldName('naam')} />
                </th>
                <th className="hand" onClick={sort('nummer')}>
                  Nummer <FontAwesomeIcon icon={getSortIconByFieldName('nummer')} />
                </th>
                <th className="hand" onClick={sort('omschrijving')}>
                  Omschrijving <FontAwesomeIcon icon={getSortIconByFieldName('omschrijving')} />
                </th>
                <th className="hand" onClick={sort('piahoofcategorieomschrijving')}>
                  Piahoofcategorieomschrijving <FontAwesomeIcon icon={getSortIconByFieldName('piahoofcategorieomschrijving')} />
                </th>
                <th className="hand" onClick={sort('piahoofdcategoriecode')}>
                  Piahoofdcategoriecode <FontAwesomeIcon icon={getSortIconByFieldName('piahoofdcategoriecode')} />
                </th>
                <th className="hand" onClick={sort('subcode')}>
                  Subcode <FontAwesomeIcon icon={getSortIconByFieldName('subcode')} />
                </th>
                <th className="hand" onClick={sort('subcodeomschrijving')}>
                  Subcodeomschrijving <FontAwesomeIcon icon={getSortIconByFieldName('subcodeomschrijving')} />
                </th>
                <th>
                  Heeft Activa <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Heeft Kostenplaats <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Valtbinnen Hoofdrekening 2 <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Wordtgeschrevenop Inkooporder <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {hoofdrekeningList.map((hoofdrekening, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/hoofdrekening/${hoofdrekening.id}`} color="link" size="sm">
                      {hoofdrekening.id}
                    </Button>
                  </td>
                  <td>{hoofdrekening.naam}</td>
                  <td>{hoofdrekening.nummer}</td>
                  <td>{hoofdrekening.omschrijving}</td>
                  <td>{hoofdrekening.piahoofcategorieomschrijving}</td>
                  <td>{hoofdrekening.piahoofdcategoriecode}</td>
                  <td>{hoofdrekening.subcode}</td>
                  <td>{hoofdrekening.subcodeomschrijving}</td>
                  <td>
                    {hoofdrekening.heeftActivas
                      ? hoofdrekening.heeftActivas.map((val, j) => (
                          <span key={j}>
                            <Link to={`/activa/${val.id}`}>{val.id}</Link>
                            {j === hoofdrekening.heeftActivas.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {hoofdrekening.heeftKostenplaats
                      ? hoofdrekening.heeftKostenplaats.map((val, j) => (
                          <span key={j}>
                            <Link to={`/kostenplaats/${val.id}`}>{val.id}</Link>
                            {j === hoofdrekening.heeftKostenplaats.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td>
                    {hoofdrekening.valtbinnenHoofdrekening2 ? (
                      <Link to={`/hoofdrekening/${hoofdrekening.valtbinnenHoofdrekening2.id}`}>
                        {hoofdrekening.valtbinnenHoofdrekening2.id}
                      </Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {hoofdrekening.wordtgeschrevenopInkooporders
                      ? hoofdrekening.wordtgeschrevenopInkooporders.map((val, j) => (
                          <span key={j}>
                            <Link to={`/inkooporder/${val.id}`}>{val.id}</Link>
                            {j === hoofdrekening.wordtgeschrevenopInkooporders.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/hoofdrekening/${hoofdrekening.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/hoofdrekening/${hoofdrekening.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/hoofdrekening/${hoofdrekening.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Hoofdrekenings found</div>
        )}
      </div>
    </div>
  );
};

export default Hoofdrekening;
