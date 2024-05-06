import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAommeldingwmojeugd } from 'app/shared/model/aommeldingwmojeugd.model';
import { getEntity, updateEntity, createEntity, reset } from './aommeldingwmojeugd.reducer';

export const AommeldingwmojeugdUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const aommeldingwmojeugdEntity = useAppSelector(state => state.aommeldingwmojeugd.entity);
  const loading = useAppSelector(state => state.aommeldingwmojeugd.loading);
  const updating = useAppSelector(state => state.aommeldingwmojeugd.updating);
  const updateSuccess = useAppSelector(state => state.aommeldingwmojeugd.updateSuccess);

  const handleClose = () => {
    navigate('/aommeldingwmojeugd');
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
      ...aommeldingwmojeugdEntity,
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
          ...aommeldingwmojeugdEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.aommeldingwmojeugd.home.createOrEditLabel" data-cy="AommeldingwmojeugdCreateUpdateHeading">
            Create or edit a Aommeldingwmojeugd
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
                <ValidatedField name="id" required readOnly id="aommeldingwmojeugd-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Aanmelder"
                id="aommeldingwmojeugd-aanmelder"
                name="aanmelder"
                data-cy="aanmelder"
                type="text"
                validate={{
                  maxLength: { value: 100, message: 'This field cannot be longer than 100 characters.' },
                }}
              />
              <ValidatedField
                label="Aanmeldingdoor"
                id="aommeldingwmojeugd-aanmeldingdoor"
                name="aanmeldingdoor"
                data-cy="aanmeldingdoor"
                type="text"
                validate={{
                  maxLength: { value: 100, message: 'This field cannot be longer than 100 characters.' },
                }}
              />
              <ValidatedField
                label="Aanmeldingdoorlandelijk"
                id="aommeldingwmojeugd-aanmeldingdoorlandelijk"
                name="aanmeldingdoorlandelijk"
                data-cy="aanmeldingdoorlandelijk"
                type="text"
                validate={{
                  maxLength: { value: 100, message: 'This field cannot be longer than 100 characters.' },
                }}
              />
              <ValidatedField
                label="Aanmeldwijze"
                id="aommeldingwmojeugd-aanmeldwijze"
                name="aanmeldwijze"
                data-cy="aanmeldwijze"
                type="text"
              />
              <ValidatedField
                label="Deskundigheid"
                id="aommeldingwmojeugd-deskundigheid"
                name="deskundigheid"
                data-cy="deskundigheid"
                type="text"
              />
              <ValidatedField
                label="Isclientopdehoogte"
                id="aommeldingwmojeugd-isclientopdehoogte"
                name="isclientopdehoogte"
                data-cy="isclientopdehoogte"
                type="text"
              />
              <ValidatedField
                label="Onderzoekswijze"
                id="aommeldingwmojeugd-onderzoekswijze"
                name="onderzoekswijze"
                data-cy="onderzoekswijze"
                type="text"
              />
              <ValidatedField
                label="Redenafsluiting"
                id="aommeldingwmojeugd-redenafsluiting"
                name="redenafsluiting"
                data-cy="redenafsluiting"
                type="text"
              />
              <ValidatedField label="Vervolg" id="aommeldingwmojeugd-vervolg" name="vervolg" data-cy="vervolg" type="text" />
              <ValidatedField label="Verwezen" id="aommeldingwmojeugd-verwezen" name="verwezen" data-cy="verwezen" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/aommeldingwmojeugd" replace color="info">
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

export default AommeldingwmojeugdUpdate;
