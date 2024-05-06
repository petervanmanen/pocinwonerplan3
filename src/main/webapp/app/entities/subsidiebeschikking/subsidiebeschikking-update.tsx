import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISubsidie } from 'app/shared/model/subsidie.model';
import { getEntities as getSubsidies } from 'app/entities/subsidie/subsidie.reducer';
import { ISubsidiebeschikking } from 'app/shared/model/subsidiebeschikking.model';
import { getEntity, updateEntity, createEntity, reset } from './subsidiebeschikking.reducer';

export const SubsidiebeschikkingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const subsidies = useAppSelector(state => state.subsidie.entities);
  const subsidiebeschikkingEntity = useAppSelector(state => state.subsidiebeschikking.entity);
  const loading = useAppSelector(state => state.subsidiebeschikking.loading);
  const updating = useAppSelector(state => state.subsidiebeschikking.updating);
  const updateSuccess = useAppSelector(state => state.subsidiebeschikking.updateSuccess);

  const handleClose = () => {
    navigate('/subsidiebeschikking');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getSubsidies({}));
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
    if (values.beschiktbedrag !== undefined && typeof values.beschiktbedrag !== 'number') {
      values.beschiktbedrag = Number(values.beschiktbedrag);
    }

    const entity = {
      ...subsidiebeschikkingEntity,
      ...values,
      betreftSubsidie: subsidies.find(it => it.id.toString() === values.betreftSubsidie?.toString()),
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
          ...subsidiebeschikkingEntity,
          betreftSubsidie: subsidiebeschikkingEntity?.betreftSubsidie?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.subsidiebeschikking.home.createOrEditLabel" data-cy="SubsidiebeschikkingCreateUpdateHeading">
            Create or edit a Subsidiebeschikking
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
                <ValidatedField name="id" required readOnly id="subsidiebeschikking-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Beschikkingsnummer"
                id="subsidiebeschikking-beschikkingsnummer"
                name="beschikkingsnummer"
                data-cy="beschikkingsnummer"
                type="text"
              />
              <ValidatedField
                label="Beschiktbedrag"
                id="subsidiebeschikking-beschiktbedrag"
                name="beschiktbedrag"
                data-cy="beschiktbedrag"
                type="text"
              />
              <ValidatedField label="Besluit" id="subsidiebeschikking-besluit" name="besluit" data-cy="besluit" type="text" />
              <ValidatedField
                label="Internkenmerk"
                id="subsidiebeschikking-internkenmerk"
                name="internkenmerk"
                data-cy="internkenmerk"
                type="text"
              />
              <ValidatedField label="Kenmerk" id="subsidiebeschikking-kenmerk" name="kenmerk" data-cy="kenmerk" type="text" />
              <ValidatedField label="Ontvangen" id="subsidiebeschikking-ontvangen" name="ontvangen" data-cy="ontvangen" type="date" />
              <ValidatedField
                label="Opmerkingen"
                id="subsidiebeschikking-opmerkingen"
                name="opmerkingen"
                data-cy="opmerkingen"
                type="text"
              />
              <ValidatedField
                id="subsidiebeschikking-betreftSubsidie"
                name="betreftSubsidie"
                data-cy="betreftSubsidie"
                label="Betreft Subsidie"
                type="select"
              >
                <option value="" key="0" />
                {subsidies
                  ? subsidies.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/subsidiebeschikking" replace color="info">
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

export default SubsidiebeschikkingUpdate;
