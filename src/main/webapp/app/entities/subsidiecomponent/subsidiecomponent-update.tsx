import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IKostenplaats } from 'app/shared/model/kostenplaats.model';
import { getEntities as getKostenplaats } from 'app/entities/kostenplaats/kostenplaats.reducer';
import { ISubsidiecomponent } from 'app/shared/model/subsidiecomponent.model';
import { getEntity, updateEntity, createEntity, reset } from './subsidiecomponent.reducer';

export const SubsidiecomponentUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const kostenplaats = useAppSelector(state => state.kostenplaats.entities);
  const subsidiecomponentEntity = useAppSelector(state => state.subsidiecomponent.entity);
  const loading = useAppSelector(state => state.subsidiecomponent.loading);
  const updating = useAppSelector(state => state.subsidiecomponent.updating);
  const updateSuccess = useAppSelector(state => state.subsidiecomponent.updateSuccess);

  const handleClose = () => {
    navigate('/subsidiecomponent');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getKostenplaats({}));
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
    if (values.gereserveerdbedrag !== undefined && typeof values.gereserveerdbedrag !== 'number') {
      values.gereserveerdbedrag = Number(values.gereserveerdbedrag);
    }
    if (values.toegekendbedrag !== undefined && typeof values.toegekendbedrag !== 'number') {
      values.toegekendbedrag = Number(values.toegekendbedrag);
    }

    const entity = {
      ...subsidiecomponentEntity,
      ...values,
      heeftKostenplaats: kostenplaats.find(it => it.id.toString() === values.heeftKostenplaats?.toString()),
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
          ...subsidiecomponentEntity,
          heeftKostenplaats: subsidiecomponentEntity?.heeftKostenplaats?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.subsidiecomponent.home.createOrEditLabel" data-cy="SubsidiecomponentCreateUpdateHeading">
            Create or edit a Subsidiecomponent
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
                <ValidatedField name="id" required readOnly id="subsidiecomponent-id" label="ID" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Gereserveerdbedrag"
                id="subsidiecomponent-gereserveerdbedrag"
                name="gereserveerdbedrag"
                data-cy="gereserveerdbedrag"
                type="text"
              />
              <ValidatedField
                label="Toegekendbedrag"
                id="subsidiecomponent-toegekendbedrag"
                name="toegekendbedrag"
                data-cy="toegekendbedrag"
                type="text"
              />
              <ValidatedField
                id="subsidiecomponent-heeftKostenplaats"
                name="heeftKostenplaats"
                data-cy="heeftKostenplaats"
                label="Heeft Kostenplaats"
                type="select"
              >
                <option value="" key="0" />
                {kostenplaats
                  ? kostenplaats.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/subsidiecomponent" replace color="info">
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

export default SubsidiecomponentUpdate;
