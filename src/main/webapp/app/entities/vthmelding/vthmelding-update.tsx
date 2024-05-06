import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IVthmelding } from 'app/shared/model/vthmelding.model';
import { getEntity, updateEntity, createEntity, reset } from './vthmelding.reducer';

export const VthmeldingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const vthmeldingEntity = useAppSelector(state => state.vthmelding.entity);
  const loading = useAppSelector(state => state.vthmelding.loading);
  const updating = useAppSelector(state => state.vthmelding.updating);
  const updateSuccess = useAppSelector(state => state.vthmelding.updateSuccess);

  const handleClose = () => {
    navigate('/vthmelding');
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
      ...vthmeldingEntity,
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
          ...vthmeldingEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.vthmelding.home.createOrEditLabel" data-cy="VthmeldingCreateUpdateHeading">
            Create or edit a Vthmelding
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="vthmelding-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Activiteit" id="vthmelding-activiteit" name="activiteit" data-cy="activiteit" type="text" />
              <ValidatedField label="Beoordeling" id="vthmelding-beoordeling" name="beoordeling" data-cy="beoordeling" type="text" />
              <ValidatedField
                label="Datumseponering"
                id="vthmelding-datumseponering"
                name="datumseponering"
                data-cy="datumseponering"
                type="date"
              />
              <ValidatedField label="Datumtijdtot" id="vthmelding-datumtijdtot" name="datumtijdtot" data-cy="datumtijdtot" type="text" />
              <ValidatedField label="Geseponeerd" id="vthmelding-geseponeerd" name="geseponeerd" data-cy="geseponeerd" type="text" />
              <ValidatedField label="Locatie" id="vthmelding-locatie" name="locatie" data-cy="locatie" type="text" />
              <ValidatedField
                label="Organisatieonderdeel"
                id="vthmelding-organisatieonderdeel"
                name="organisatieonderdeel"
                data-cy="organisatieonderdeel"
                type="text"
              />
              <ValidatedField
                label="Overtredingscode"
                id="vthmelding-overtredingscode"
                name="overtredingscode"
                data-cy="overtredingscode"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField
                label="Overtredingsgroep"
                id="vthmelding-overtredingsgroep"
                name="overtredingsgroep"
                data-cy="overtredingsgroep"
                type="text"
              />
              <ValidatedField
                label="Referentienummer"
                id="vthmelding-referentienummer"
                name="referentienummer"
                data-cy="referentienummer"
                type="text"
              />
              <ValidatedField label="Resultaat" id="vthmelding-resultaat" name="resultaat" data-cy="resultaat" type="text" />
              <ValidatedField
                label="Soortvthmelding"
                id="vthmelding-soortvthmelding"
                name="soortvthmelding"
                data-cy="soortvthmelding"
                type="text"
              />
              <ValidatedField label="Status" id="vthmelding-status" name="status" data-cy="status" type="text" />
              <ValidatedField label="Straatnaam" id="vthmelding-straatnaam" name="straatnaam" data-cy="straatnaam" type="text" />
              <ValidatedField label="Taaktype" id="vthmelding-taaktype" name="taaktype" data-cy="taaktype" type="text" />
              <ValidatedField
                label="Zaaknummer"
                id="vthmelding-zaaknummer"
                name="zaaknummer"
                data-cy="zaaknummer"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/vthmelding" replace color="info">
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

export default VthmeldingUpdate;
