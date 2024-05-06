import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAanbesteding } from 'app/shared/model/aanbesteding.model';
import { getEntities as getAanbestedings } from 'app/entities/aanbesteding/aanbesteding.reducer';
import { IAankondiging } from 'app/shared/model/aankondiging.model';
import { getEntity, updateEntity, createEntity, reset } from './aankondiging.reducer';

export const AankondigingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const aanbestedings = useAppSelector(state => state.aanbesteding.entities);
  const aankondigingEntity = useAppSelector(state => state.aankondiging.entity);
  const loading = useAppSelector(state => state.aankondiging.loading);
  const updating = useAppSelector(state => state.aankondiging.updating);
  const updateSuccess = useAppSelector(state => state.aankondiging.updateSuccess);

  const handleClose = () => {
    navigate('/aankondiging');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getAanbestedings({}));
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
      ...aankondigingEntity,
      ...values,
      mondtuitAanbesteding: aanbestedings.find(it => it.id.toString() === values.mondtuitAanbesteding?.toString()),
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
          ...aankondigingEntity,
          mondtuitAanbesteding: aankondigingEntity?.mondtuitAanbesteding?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.aankondiging.home.createOrEditLabel" data-cy="AankondigingCreateUpdateHeading">
            Create or edit a Aankondiging
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="aankondiging-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Beschrijving" id="aankondiging-beschrijving" name="beschrijving" data-cy="beschrijving" type="text" />
              <ValidatedField label="Categorie" id="aankondiging-categorie" name="categorie" data-cy="categorie" type="text" />
              <ValidatedField label="Datum" id="aankondiging-datum" name="datum" data-cy="datum" type="text" />
              <ValidatedField label="Naam" id="aankondiging-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField label="Type" id="aankondiging-type" name="type" data-cy="type" type="text" />
              <ValidatedField
                id="aankondiging-mondtuitAanbesteding"
                name="mondtuitAanbesteding"
                data-cy="mondtuitAanbesteding"
                label="Mondtuit Aanbesteding"
                type="select"
              >
                <option value="" key="0" />
                {aanbestedings
                  ? aanbestedings.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/aankondiging" replace color="info">
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

export default AankondigingUpdate;
