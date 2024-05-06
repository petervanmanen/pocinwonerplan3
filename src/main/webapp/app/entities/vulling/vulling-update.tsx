import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISpoor } from 'app/shared/model/spoor.model';
import { getEntities as getSpoors } from 'app/entities/spoor/spoor.reducer';
import { IVulling } from 'app/shared/model/vulling.model';
import { getEntity, updateEntity, createEntity, reset } from './vulling.reducer';

export const VullingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const spoors = useAppSelector(state => state.spoor.entities);
  const vullingEntity = useAppSelector(state => state.vulling.entity);
  const loading = useAppSelector(state => state.vulling.loading);
  const updating = useAppSelector(state => state.vulling.updating);
  const updateSuccess = useAppSelector(state => state.vulling.updateSuccess);

  const handleClose = () => {
    navigate('/vulling');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getSpoors({}));
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
      ...vullingEntity,
      ...values,
      heeftSpoor: spoors.find(it => it.id.toString() === values.heeftSpoor?.toString()),
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
          ...vullingEntity,
          heeftSpoor: vullingEntity?.heeftSpoor?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.vulling.home.createOrEditLabel" data-cy="VullingCreateUpdateHeading">
            Create or edit a Vulling
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="vulling-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Grondsoort" id="vulling-grondsoort" name="grondsoort" data-cy="grondsoort" type="text" />
              <ValidatedField label="Key" id="vulling-key" name="key" data-cy="key" type="text" />
              <ValidatedField label="Keyspoor" id="vulling-keyspoor" name="keyspoor" data-cy="keyspoor" type="text" />
              <ValidatedField label="Kleur" id="vulling-kleur" name="kleur" data-cy="kleur" type="text" />
              <ValidatedField
                label="Projectcd"
                id="vulling-projectcd"
                name="projectcd"
                data-cy="projectcd"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField label="Putnummer" id="vulling-putnummer" name="putnummer" data-cy="putnummer" type="text" />
              <ValidatedField
                label="Spoornummer"
                id="vulling-spoornummer"
                name="spoornummer"
                data-cy="spoornummer"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField label="Structuur" id="vulling-structuur" name="structuur" data-cy="structuur" type="text" />
              <ValidatedField
                label="Vlaknummer"
                id="vulling-vlaknummer"
                name="vlaknummer"
                data-cy="vlaknummer"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField
                label="Vullingnummer"
                id="vulling-vullingnummer"
                name="vullingnummer"
                data-cy="vullingnummer"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField id="vulling-heeftSpoor" name="heeftSpoor" data-cy="heeftSpoor" label="Heeft Spoor" type="select">
                <option value="" key="0" />
                {spoors
                  ? spoors.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/vulling" replace color="info">
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

export default VullingUpdate;
