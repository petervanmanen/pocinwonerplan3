import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './begrotingregel.reducer';

export const Begrotingregel = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const begrotingregelList = useAppSelector(state => state.begrotingregel.entities);
  const loading = useAppSelector(state => state.begrotingregel.loading);

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
      <h2 id="begrotingregel-heading" data-cy="BegrotingregelHeading">
        Begrotingregels
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/begrotingregel/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Begrotingregel
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {begrotingregelList && begrotingregelList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('batenlasten')}>
                  Batenlasten <FontAwesomeIcon icon={getSortIconByFieldName('batenlasten')} />
                </th>
                <th className="hand" onClick={sort('bedrag')}>
                  Bedrag <FontAwesomeIcon icon={getSortIconByFieldName('bedrag')} />
                </th>
                <th className="hand" onClick={sort('soortregel')}>
                  Soortregel <FontAwesomeIcon icon={getSortIconByFieldName('soortregel')} />
                </th>
                <th>
                  Betreft Doelstelling <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Betreft Product <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Betreft Kostenplaats <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Betreft Hoofdrekening <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Betreft Hoofdstuk <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Heeft Begroting <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {begrotingregelList.map((begrotingregel, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/begrotingregel/${begrotingregel.id}`} color="link" size="sm">
                      {begrotingregel.id}
                    </Button>
                  </td>
                  <td>{begrotingregel.batenlasten}</td>
                  <td>{begrotingregel.bedrag}</td>
                  <td>{begrotingregel.soortregel}</td>
                  <td>
                    {begrotingregel.betreftDoelstelling ? (
                      <Link to={`/doelstelling/${begrotingregel.betreftDoelstelling.id}`}>{begrotingregel.betreftDoelstelling.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {begrotingregel.betreftProduct ? (
                      <Link to={`/product/${begrotingregel.betreftProduct.id}`}>{begrotingregel.betreftProduct.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {begrotingregel.betreftKostenplaats ? (
                      <Link to={`/kostenplaats/${begrotingregel.betreftKostenplaats.id}`}>{begrotingregel.betreftKostenplaats.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {begrotingregel.betreftHoofdrekening ? (
                      <Link to={`/hoofdrekening/${begrotingregel.betreftHoofdrekening.id}`}>{begrotingregel.betreftHoofdrekening.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {begrotingregel.betreftHoofdstuk ? (
                      <Link to={`/hoofdstuk/${begrotingregel.betreftHoofdstuk.id}`}>{begrotingregel.betreftHoofdstuk.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {begrotingregel.heeftBegroting ? (
                      <Link to={`/begroting/${begrotingregel.heeftBegroting.id}`}>{begrotingregel.heeftBegroting.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/begrotingregel/${begrotingregel.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/begrotingregel/${begrotingregel.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/begrotingregel/${begrotingregel.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Begrotingregels found</div>
        )}
      </div>
    </div>
  );
};

export default Begrotingregel;
