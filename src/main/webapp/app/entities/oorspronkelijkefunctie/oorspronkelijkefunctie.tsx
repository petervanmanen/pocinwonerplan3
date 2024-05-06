import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './oorspronkelijkefunctie.reducer';

export const Oorspronkelijkefunctie = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const oorspronkelijkefunctieList = useAppSelector(state => state.oorspronkelijkefunctie.entities);
  const loading = useAppSelector(state => state.oorspronkelijkefunctie.loading);

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
      <h2 id="oorspronkelijkefunctie-heading" data-cy="OorspronkelijkefunctieHeading">
        Oorspronkelijkefuncties
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link
            to="/oorspronkelijkefunctie/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Oorspronkelijkefunctie
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {oorspronkelijkefunctieList && oorspronkelijkefunctieList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('functie')}>
                  Functie <FontAwesomeIcon icon={getSortIconByFieldName('functie')} />
                </th>
                <th className="hand" onClick={sort('functiesoort')}>
                  Functiesoort <FontAwesomeIcon icon={getSortIconByFieldName('functiesoort')} />
                </th>
                <th className="hand" onClick={sort('hoofdcategorie')}>
                  Hoofdcategorie <FontAwesomeIcon icon={getSortIconByFieldName('hoofdcategorie')} />
                </th>
                <th className="hand" onClick={sort('hoofdfunctie')}>
                  Hoofdfunctie <FontAwesomeIcon icon={getSortIconByFieldName('hoofdfunctie')} />
                </th>
                <th className="hand" onClick={sort('subcategorie')}>
                  Subcategorie <FontAwesomeIcon icon={getSortIconByFieldName('subcategorie')} />
                </th>
                <th className="hand" onClick={sort('toelichting')}>
                  Toelichting <FontAwesomeIcon icon={getSortIconByFieldName('toelichting')} />
                </th>
                <th className="hand" onClick={sort('verbijzondering')}>
                  Verbijzondering <FontAwesomeIcon icon={getSortIconByFieldName('verbijzondering')} />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {oorspronkelijkefunctieList.map((oorspronkelijkefunctie, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/oorspronkelijkefunctie/${oorspronkelijkefunctie.id}`} color="link" size="sm">
                      {oorspronkelijkefunctie.id}
                    </Button>
                  </td>
                  <td>{oorspronkelijkefunctie.functie}</td>
                  <td>{oorspronkelijkefunctie.functiesoort}</td>
                  <td>{oorspronkelijkefunctie.hoofdcategorie}</td>
                  <td>{oorspronkelijkefunctie.hoofdfunctie}</td>
                  <td>{oorspronkelijkefunctie.subcategorie}</td>
                  <td>{oorspronkelijkefunctie.toelichting}</td>
                  <td>{oorspronkelijkefunctie.verbijzondering}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button
                        tag={Link}
                        to={`/oorspronkelijkefunctie/${oorspronkelijkefunctie.id}`}
                        color="info"
                        size="sm"
                        data-cy="entityDetailsButton"
                      >
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/oorspronkelijkefunctie/${oorspronkelijkefunctie.id}/edit`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/oorspronkelijkefunctie/${oorspronkelijkefunctie.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Oorspronkelijkefuncties found</div>
        )}
      </div>
    </div>
  );
};

export default Oorspronkelijkefunctie;
