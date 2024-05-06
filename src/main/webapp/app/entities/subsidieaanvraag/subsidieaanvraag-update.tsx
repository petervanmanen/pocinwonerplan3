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
import { getEntities as getSubsidiebeschikkings } from 'app/entities/subsidiebeschikking/subsidiebeschikking.reducer';
import { ISubsidieaanvraag } from 'app/shared/model/subsidieaanvraag.model';
import { getEntity, updateEntity, createEntity, reset } from './subsidieaanvraag.reducer';

export const SubsidieaanvraagUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const subsidies = useAppSelector(state => state.subsidie.entities);
  const subsidiebeschikkings = useAppSelector(state => state.subsidiebeschikking.entities);
  const subsidieaanvraagEntity = useAppSelector(state => state.subsidieaanvraag.entity);
  const loading = useAppSelector(state => state.subsidieaanvraag.loading);
  const updating = useAppSelector(state => state.subsidieaanvraag.updating);
  const updateSuccess = useAppSelector(state => state.subsidieaanvraag.updateSuccess);

  const handleClose = () => {
    navigate('/subsidieaanvraag');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getSubsidies({}));
    dispatch(getSubsidiebeschikkings({}));
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
    if (values.aangevraagdbedrag !== undefined && typeof values.aangevraagdbedrag !== 'number') {
      values.aangevraagdbedrag = Number(values.aangevraagdbedrag);
    }

    const entity = {
      ...subsidieaanvraagEntity,
      ...values,
      betreftSubsidie: subsidies.find(it => it.id.toString() === values.betreftSubsidie?.toString()),
      mondtuitSubsidiebeschikking: subsidiebeschikkings.find(it => it.id.toString() === values.mondtuitSubsidiebeschikking?.toString()),
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
          ...subsidieaanvraagEntity,
          betreftSubsidie: subsidieaanvraagEntity?.betreftSubsidie?.id,
          mondtuitSubsidiebeschikking: subsidieaanvraagEntity?.mondtuitSubsidiebeschikking?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.subsidieaanvraag.home.createOrEditLabel" data-cy="SubsidieaanvraagCreateUpdateHeading">
            Create or edit a Subsidieaanvraag
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
                <ValidatedField name="id" required readOnly id="subsidieaanvraag-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Aangevraagdbedrag"
                id="subsidieaanvraag-aangevraagdbedrag"
                name="aangevraagdbedrag"
                data-cy="aangevraagdbedrag"
                type="text"
              />
              <ValidatedField
                label="Datumindiening"
                id="subsidieaanvraag-datumindiening"
                name="datumindiening"
                data-cy="datumindiening"
                type="date"
              />
              <ValidatedField label="Kenmerk" id="subsidieaanvraag-kenmerk" name="kenmerk" data-cy="kenmerk" type="text" />
              <ValidatedField
                label="Ontvangstbevestiging"
                id="subsidieaanvraag-ontvangstbevestiging"
                name="ontvangstbevestiging"
                data-cy="ontvangstbevestiging"
                type="date"
              />
              <ValidatedField
                label="Verwachtebeschikking"
                id="subsidieaanvraag-verwachtebeschikking"
                name="verwachtebeschikking"
                data-cy="verwachtebeschikking"
                type="date"
              />
              <ValidatedField
                id="subsidieaanvraag-betreftSubsidie"
                name="betreftSubsidie"
                data-cy="betreftSubsidie"
                label="Betreft Subsidie"
                type="select"
                required
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
              <FormText>This field is required.</FormText>
              <ValidatedField
                id="subsidieaanvraag-mondtuitSubsidiebeschikking"
                name="mondtuitSubsidiebeschikking"
                data-cy="mondtuitSubsidiebeschikking"
                label="Mondtuit Subsidiebeschikking"
                type="select"
                required
              >
                <option value="" key="0" />
                {subsidiebeschikkings
                  ? subsidiebeschikkings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/subsidieaanvraag" replace color="info">
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

export default SubsidieaanvraagUpdate;
