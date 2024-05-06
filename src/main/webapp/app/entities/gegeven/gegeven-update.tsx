import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IClassificatie } from 'app/shared/model/classificatie.model';
import { getEntities as getClassificaties } from 'app/entities/classificatie/classificatie.reducer';
import { IApplicatie } from 'app/shared/model/applicatie.model';
import { getEntities as getApplicaties } from 'app/entities/applicatie/applicatie.reducer';
import { IGegeven } from 'app/shared/model/gegeven.model';
import { getEntity, updateEntity, createEntity, reset } from './gegeven.reducer';

export const GegevenUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const classificaties = useAppSelector(state => state.classificatie.entities);
  const applicaties = useAppSelector(state => state.applicatie.entities);
  const gegevenEntity = useAppSelector(state => state.gegeven.entity);
  const loading = useAppSelector(state => state.gegeven.loading);
  const updating = useAppSelector(state => state.gegeven.updating);
  const updateSuccess = useAppSelector(state => state.gegeven.updateSuccess);

  const handleClose = () => {
    navigate('/gegeven');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getClassificaties({}));
    dispatch(getApplicaties({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  // eslint-disable-next-line complexity
  const saveEntity = values => {
    const entity = {
      ...gegevenEntity,
      ...values,
      geclassificeerdalsClassificaties: mapIdList(values.geclassificeerdalsClassificaties),
      bevatApplicatie: applicaties.find(it => it.id.toString() === values.bevatApplicatie?.toString()),
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
          ...gegevenEntity,
          geclassificeerdalsClassificaties: gegevenEntity?.geclassificeerdalsClassificaties?.map(e => e.id.toString()),
          bevatApplicatie: gegevenEntity?.bevatApplicatie?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.gegeven.home.createOrEditLabel" data-cy="GegevenCreateUpdateHeading">
            Create or edit a Gegeven
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="gegeven-id" label="Id" validate={{ required: true }} /> : null}
              <ValidatedField label="Alias" id="gegeven-alias" name="alias" data-cy="alias" type="text" />
              <ValidatedField label="Eaguid" id="gegeven-eaguid" name="eaguid" data-cy="eaguid" type="text" />
              <ValidatedField label="Naam" id="gegeven-naam" name="naam" data-cy="naam" type="text" />
              <ValidatedField label="Stereotype" id="gegeven-stereotype" name="stereotype" data-cy="stereotype" type="text" />
              <ValidatedField label="Toelichting" id="gegeven-toelichting" name="toelichting" data-cy="toelichting" type="text" />
              <ValidatedField
                label="Geclassificeerdals Classificatie"
                id="gegeven-geclassificeerdalsClassificatie"
                data-cy="geclassificeerdalsClassificatie"
                type="select"
                multiple
                name="geclassificeerdalsClassificaties"
              >
                <option value="" key="0" />
                {classificaties
                  ? classificaties.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="gegeven-bevatApplicatie"
                name="bevatApplicatie"
                data-cy="bevatApplicatie"
                label="Bevat Applicatie"
                type="select"
              >
                <option value="" key="0" />
                {applicaties
                  ? applicaties.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/gegeven" replace color="info">
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

export default GegevenUpdate;
