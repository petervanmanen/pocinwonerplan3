import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMaatschappelijkeactiviteit } from 'app/shared/model/maatschappelijkeactiviteit.model';
import { getEntity, updateEntity, createEntity, reset } from './maatschappelijkeactiviteit.reducer';

export const MaatschappelijkeactiviteitUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const maatschappelijkeactiviteitEntity = useAppSelector(state => state.maatschappelijkeactiviteit.entity);
  const loading = useAppSelector(state => state.maatschappelijkeactiviteit.loading);
  const updating = useAppSelector(state => state.maatschappelijkeactiviteit.updating);
  const updateSuccess = useAppSelector(state => state.maatschappelijkeactiviteit.updateSuccess);

  const handleClose = () => {
    navigate('/maatschappelijkeactiviteit');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }

    const entity = {
      ...maatschappelijkeactiviteitEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...maatschappelijkeactiviteitEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.maatschappelijkeactiviteit.home.createOrEditLabel" data-cy="MaatschappelijkeactiviteitCreateUpdateHeading">
            Create or edit a Maatschappelijkeactiviteit
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField name="id" required readOnly id="maatschappelijkeactiviteit-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Adresbinnenland"
                id="maatschappelijkeactiviteit-adresbinnenland"
                name="adresbinnenland"
                data-cy="adresbinnenland"
                type="text"
              />
              <ValidatedField
                label="Adrescorrespondentie"
                id="maatschappelijkeactiviteit-adrescorrespondentie"
                name="adrescorrespondentie"
                data-cy="adrescorrespondentie"
                type="text"
                validate={{
                  maxLength: { value: 100, message: 'This field cannot be longer than 100 characters.' },
                }}
              />
              <ValidatedField
                label="Datumaanvang"
                id="maatschappelijkeactiviteit-datumaanvang"
                name="datumaanvang"
                data-cy="datumaanvang"
                type="date"
              />
              <ValidatedField
                label="Datumeindegeldig"
                id="maatschappelijkeactiviteit-datumeindegeldig"
                name="datumeindegeldig"
                data-cy="datumeindegeldig"
                type="date"
              />
              <ValidatedField
                label="Datumfaillisement"
                id="maatschappelijkeactiviteit-datumfaillisement"
                name="datumfaillisement"
                data-cy="datumfaillisement"
                type="date"
              />
              <ValidatedField
                label="Indicatieeconomischactief"
                id="maatschappelijkeactiviteit-indicatieeconomischactief"
                name="indicatieeconomischactief"
                data-cy="indicatieeconomischactief"
                type="text"
              />
              <ValidatedField
                label="Kvknummer"
                id="maatschappelijkeactiviteit-kvknummer"
                name="kvknummer"
                data-cy="kvknummer"
                type="text"
                validate={{
                  maxLength: { value: 8, message: 'This field cannot be longer than 8 characters.' },
                }}
              />
              <ValidatedField
                label="Rechtsvorm"
                id="maatschappelijkeactiviteit-rechtsvorm"
                name="rechtsvorm"
                data-cy="rechtsvorm"
                type="text"
                validate={{
                  maxLength: { value: 100, message: 'This field cannot be longer than 100 characters.' },
                }}
              />
              <ValidatedField
                label="Rsin"
                id="maatschappelijkeactiviteit-rsin"
                name="rsin"
                data-cy="rsin"
                type="text"
                validate={{
                  maxLength: { value: 10, message: 'This field cannot be longer than 10 characters.' },
                }}
              />
              <ValidatedField
                label="Statutairenaam"
                id="maatschappelijkeactiviteit-statutairenaam"
                name="statutairenaam"
                data-cy="statutairenaam"
                type="text"
                validate={{
                  maxLength: { value: 100, message: 'This field cannot be longer than 100 characters.' },
                }}
              />
              <ValidatedField
                label="Telefoonnummer"
                id="maatschappelijkeactiviteit-telefoonnummer"
                name="telefoonnummer"
                data-cy="telefoonnummer"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField
                label="Url"
                id="maatschappelijkeactiviteit-url"
                name="url"
                data-cy="url"
                type="text"
                validate={{
                  maxLength: { value: 100, message: 'This field cannot be longer than 100 characters.' },
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/maatschappelijkeactiviteit" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default MaatschappelijkeactiviteitUpdate;
