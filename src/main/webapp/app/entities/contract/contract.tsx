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

import { getEntities } from './contract.reducer';

export const Contract = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();
  const navigate = useNavigate();

  const [sortState, setSortState] = useState(overrideSortStateWithQueryParams(getSortState(pageLocation, 'id'), pageLocation.search));

  const contractList = useAppSelector(state => state.contract.entities);
  const loading = useAppSelector(state => state.contract.loading);

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
      <h2 id="contract-heading" data-cy="ContractHeading">
        Contracts
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh list
          </Button>
          <Link to="/contract/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create a new Contract
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {contractList && contractList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  ID <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                </th>
                <th className="hand" onClick={sort('autorisatiegroep')}>
                  Autorisatiegroep <FontAwesomeIcon icon={getSortIconByFieldName('autorisatiegroep')} />
                </th>
                <th className="hand" onClick={sort('beschrijving')}>
                  Beschrijving <FontAwesomeIcon icon={getSortIconByFieldName('beschrijving')} />
                </th>
                <th className="hand" onClick={sort('categorie')}>
                  Categorie <FontAwesomeIcon icon={getSortIconByFieldName('categorie')} />
                </th>
                <th className="hand" onClick={sort('classificatie')}>
                  Classificatie <FontAwesomeIcon icon={getSortIconByFieldName('classificatie')} />
                </th>
                <th className="hand" onClick={sort('contractrevisie')}>
                  Contractrevisie <FontAwesomeIcon icon={getSortIconByFieldName('contractrevisie')} />
                </th>
                <th className="hand" onClick={sort('datumcreatie')}>
                  Datumcreatie <FontAwesomeIcon icon={getSortIconByFieldName('datumcreatie')} />
                </th>
                <th className="hand" onClick={sort('datumeinde')}>
                  Datumeinde <FontAwesomeIcon icon={getSortIconByFieldName('datumeinde')} />
                </th>
                <th className="hand" onClick={sort('datumstart')}>
                  Datumstart <FontAwesomeIcon icon={getSortIconByFieldName('datumstart')} />
                </th>
                <th className="hand" onClick={sort('groep')}>
                  Groep <FontAwesomeIcon icon={getSortIconByFieldName('groep')} />
                </th>
                <th className="hand" onClick={sort('interncontractid')}>
                  Interncontractid <FontAwesomeIcon icon={getSortIconByFieldName('interncontractid')} />
                </th>
                <th className="hand" onClick={sort('interncontractrevisie')}>
                  Interncontractrevisie <FontAwesomeIcon icon={getSortIconByFieldName('interncontractrevisie')} />
                </th>
                <th className="hand" onClick={sort('opmerkingen')}>
                  Opmerkingen <FontAwesomeIcon icon={getSortIconByFieldName('opmerkingen')} />
                </th>
                <th className="hand" onClick={sort('status')}>
                  Status <FontAwesomeIcon icon={getSortIconByFieldName('status')} />
                </th>
                <th className="hand" onClick={sort('type')}>
                  Type <FontAwesomeIcon icon={getSortIconByFieldName('type')} />
                </th>
                <th className="hand" onClick={sort('voorwaarde')}>
                  Voorwaarde <FontAwesomeIcon icon={getSortIconByFieldName('voorwaarde')} />
                </th>
                <th className="hand" onClick={sort('zoekwoorden')}>
                  Zoekwoorden <FontAwesomeIcon icon={getSortIconByFieldName('zoekwoorden')} />
                </th>
                <th>
                  Bovenliggend Contract <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Heeft Leverancier <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Contractant Leverancier <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {contractList.map((contract, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/contract/${contract.id}`} color="link" size="sm">
                      {contract.id}
                    </Button>
                  </td>
                  <td>{contract.autorisatiegroep}</td>
                  <td>{contract.beschrijving}</td>
                  <td>{contract.categorie}</td>
                  <td>{contract.classificatie}</td>
                  <td>{contract.contractrevisie}</td>
                  <td>
                    {contract.datumcreatie ? <TextFormat type="date" value={contract.datumcreatie} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {contract.datumeinde ? <TextFormat type="date" value={contract.datumeinde} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>
                    {contract.datumstart ? <TextFormat type="date" value={contract.datumstart} format={APP_LOCAL_DATE_FORMAT} /> : null}
                  </td>
                  <td>{contract.groep}</td>
                  <td>{contract.interncontractid}</td>
                  <td>{contract.interncontractrevisie}</td>
                  <td>{contract.opmerkingen}</td>
                  <td>{contract.status}</td>
                  <td>{contract.type}</td>
                  <td>{contract.voorwaarde}</td>
                  <td>{contract.zoekwoorden}</td>
                  <td>
                    {contract.bovenliggendContract ? (
                      <Link to={`/contract/${contract.bovenliggendContract.id}`}>{contract.bovenliggendContract.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {contract.heeftLeverancier ? (
                      <Link to={`/leverancier/${contract.heeftLeverancier.id}`}>{contract.heeftLeverancier.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td>
                    {contract.contractantLeverancier ? (
                      <Link to={`/leverancier/${contract.contractantLeverancier.id}`}>{contract.contractantLeverancier.id}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/contract/${contract.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/contract/${contract.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button
                        onClick={() => (window.location.href = `/contract/${contract.id}/delete`)}
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
          !loading && <div className="alert alert-warning">No Contracts found</div>
        )}
      </div>
    </div>
  );
};

export default Contract;
