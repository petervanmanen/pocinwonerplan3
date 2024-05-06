import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IContainer } from 'app/shared/model/container.model';
import { getEntities as getContainers } from 'app/entities/container/container.reducer';
import { IVulgraadmeting } from 'app/shared/model/vulgraadmeting.model';
import { getEntity, updateEntity, createEntity, reset } from './vulgraadmeting.reducer';

export const VulgraadmetingUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const containers = useAppSelector(state => state.container.entities);
  const vulgraadmetingEntity = useAppSelector(state => state.vulgraadmeting.entity);
  const loading = useAppSelector(state => state.vulgraadmeting.loading);
  const updating = useAppSelector(state => state.vulgraadmeting.updating);
  const updateSuccess = useAppSelector(state => state.vulgraadmeting.updateSuccess);

  const handleClose = () => {
    navigate('/vulgraadmeting');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getContainers({}));
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
      ...vulgraadmetingEntity,
      ...values,
      heeftContainer: containers.find(it => it.id.toString() === values.heeftContainer?.toString()),
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
          ...vulgraadmetingEntity,
          heeftContainer: vulgraadmetingEntity?.heeftContainer?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.vulgraadmeting.home.createOrEditLabel" data-cy="VulgraadmetingCreateUpdateHeading">
            Create or edit a Vulgraadmeting
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
                <ValidatedField name="id" required readOnly id="vulgraadmeting-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField label="Tijdstip" id="vulgraadmeting-tijdstip" name="tijdstip" data-cy="tijdstip" type="text" />
              <ValidatedField label="Vulgraad" id="vulgraadmeting-vulgraad" name="vulgraad" data-cy="vulgraad" type="text" />
              <ValidatedField
                label="Vullinggewicht"
                id="vulgraadmeting-vullinggewicht"
                name="vullinggewicht"
                data-cy="vullinggewicht"
                type="text"
              />
              <ValidatedField
                id="vulgraadmeting-heeftContainer"
                name="heeftContainer"
                data-cy="heeftContainer"
                label="Heeft Container"
                type="select"
              >
                <option value="" key="0" />
                {containers
                  ? containers.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/vulgraadmeting" replace color="info">
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

export default VulgraadmetingUpdate;
