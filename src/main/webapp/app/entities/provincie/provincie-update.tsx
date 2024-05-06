import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IProvincie } from 'app/shared/model/provincie.model';
import { getEntity, updateEntity, createEntity, reset } from './provincie.reducer';

export const ProvincieUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const provincieEntity = useAppSelector(state => state.provincie.entity);
  const loading = useAppSelector(state => state.provincie.loading);
  const updating = useAppSelector(state => state.provincie.updating);
  const updateSuccess = useAppSelector(state => state.provincie.updateSuccess);

  const handleClose = () => {
    navigate('/provincie');
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
      ...provincieEntity,
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
          ...provincieEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.provincie.home.createOrEditLabel" data-cy="ProvincieCreateUpdateHeading">
            Create or edit a Provincie
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="provincie-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Datumeindeprovincie"
                id="provincie-datumeindeprovincie"
                name="datumeindeprovincie"
                data-cy="datumeindeprovincie"
                type="date"
              />
              <ValidatedField
                label="Datumingangprovincie"
                id="provincie-datumingangprovincie"
                name="datumingangprovincie"
                data-cy="datumingangprovincie"
                type="date"
              />
              <ValidatedField label="Hoofdstad" id="provincie-hoofdstad" name="hoofdstad" data-cy="hoofdstad" type="text" />
              <ValidatedField label="Oppervlakte" id="provincie-oppervlakte" name="oppervlakte" data-cy="oppervlakte" type="text" />
              <ValidatedField
                label="Oppervlakteland"
                id="provincie-oppervlakteland"
                name="oppervlakteland"
                data-cy="oppervlakteland"
                type="text"
              />
              <ValidatedField label="Provinciecode" id="provincie-provinciecode" name="provinciecode" data-cy="provinciecode" type="text" />
              <ValidatedField label="Provincienaam" id="provincie-provincienaam" name="provincienaam" data-cy="provincienaam" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/provincie" replace color="info">
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

export default ProvincieUpdate;
