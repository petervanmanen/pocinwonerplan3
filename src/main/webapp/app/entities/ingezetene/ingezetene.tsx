import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, SORT } from 'app/shared/util/pagination.constants';
import { overrideSortStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities } from './ingezetene.reducer';

export const Ingezetene = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const ingezeteneList = useAppSelector(state => state.ingezetene.entities);
  const loading = useAppSelector(state => state.ingezetene.loading);

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
      <h2 id="ingezetene-heading" data-cy="IngezeteneHeading">
        Ingezetenes
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/ingezetene/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Ingezetene
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {ingezeteneList && ingezeteneList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('aanduidingeuropeeskiesrecht')}>
                  Aanduidingeuropeeskiesrecht <FontAwesomeIcon icon={getSortIconByFieldName('aanduidingeuropeeskiesrecht')} />
                </th>
                <th className="hand" onClick={sort('aanduidinguitgeslotenkiesrecht')}>
                  Aanduidinguitgeslotenkiesrecht <FontAwesomeIcon icon={getSortIconByFieldName('aanduidinguitgeslotenkiesrecht')} />
                </th>
                <th className="hand" onClick={sort('datumverkrijgingverblijfstitel')}>
                  Datumverkrijgingverblijfstitel <FontAwesomeIcon icon={getSortIconByFieldName('datumverkrijgingverblijfstitel')} />
                </th>
                <th className="hand" onClick={sort('datumverliesverblijfstitel')}>
                  Datumverliesverblijfstitel <FontAwesomeIcon icon={getSortIconByFieldName('datumverliesverblijfstitel')} />
                </th>
                <th className="hand" onClick={sort('indicatieblokkering')}>
                  Indicatieblokkering <FontAwesomeIcon icon={getSortIconByFieldName('indicatieblokkering')} />
                </th>
                <th className="hand" onClick={sort('indicatiecurateleregister')}>
                  Indicatiecurateleregister <FontAwesomeIcon icon={getSortIconByFieldName('indicatiecurateleregister')} />
                </th>
                <th className="hand" onClick={sort('indicatiegezagminderjarige')}>
                  Indicatiegezagminderjarige <FontAwesomeIcon icon={getSortIconByFieldName('indicatiegezagminderjarige')} />
                </th>
                <th>
                  Heeft Verblijfstitel <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {ingezeteneList.map((ingezetene, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/ingezetene/${ingezetene.id}`} color="link" size="sm">
                      {ingezetene.id}
                    </Button>
                  </td>
                  <td>{ingezetene.aanduidingeuropeeskiesrecht ? 'true' : 'false'}</td>
                  <td>{ingezetene.aanduidinguitgeslotenkiesrecht ? 'true' : 'false'}</td>
                  <td>{ingezetene.datumverkrijgingverblijfstitel}</td>
                  <td>{ingezetene.datumverliesverblijfstitel}</td>
                  <td>{ingezetene.indicatieblokkering}</td>
                  <td>{ingezetene.indicatiecurateleregister}</td>
                  <td>{ingezetene.indicatiegezagminderjarige}</td>
                  <td>
                    {ingezetene.heeftVerblijfstitel ? (
                      <Link to={`/verblijfstitel/${ingezetene.heeftVerblijfstitel.id}`}>{ingezetene.heeftVerblijfstitel.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/ingezetene/${ingezetene.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/ingezetene/${ingezetene.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/ingezetene/${ingezetene.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Ingezetenes found</div>
        )}
      </div>
    </div>
  );
};

export default Ingezetene;
