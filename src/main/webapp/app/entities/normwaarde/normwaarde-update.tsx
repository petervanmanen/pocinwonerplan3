import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILocatie } from 'app/shared/model/locatie.model';
import { getEntities as getLocaties } from 'app/entities/locatie/locatie.reducer';
import { INorm } from 'app/shared/model/norm.model';
import { getEntities as getNorms } from 'app/entities/norm/norm.reducer';
import { INormwaarde } from 'app/shared/model/normwaarde.model';
import { getEntity, updateEntity, createEntity, reset } from './normwaarde.reducer';

export const NormwaardeUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const locaties = useAppSelector(state => state.locatie.entities);
  const norms = useAppSelector(state => state.norm.entities);
  const normwaardeEntity = useAppSelector(state => state.normwaarde.entity);
  const loading = useAppSelector(state => state.normwaarde.loading);
  const updating = useAppSelector(state => state.normwaarde.updating);
  const updateSuccess = useAppSelector(state => state.normwaarde.updateSuccess);

  const handleClose = () => {
    navigate('/normwaarde');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getLocaties({}));
    dispatch(getNorms({}));
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
      ...normwaardeEntity,
      ...values,
      geldtvoorLocaties: mapIdList(values.geldtvoorLocaties),
      bevatNorm: norms.find(it => it.id.toString() === values.bevatNorm?.toString()),
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
          ...normwaardeEntity,
          geldtvoorLocaties: normwaardeEntity?.geldtvoorLocaties?.map(e => e.id.toString()),
          bevatNorm: normwaardeEntity?.bevatNorm?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.normwaarde.home.createOrEditLabel" data-cy="NormwaardeCreateUpdateHeading">
            Create or edit a Normwaarde
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="normwaarde-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Kwalitatievewaarde"
                id="normwaarde-kwalitatievewaarde"
                name="kwalitatievewaarde"
                data-cy="kwalitatievewaarde"
                type="text"
              />
              <ValidatedField
                label="Kwantitatievewaardeeenheid"
                id="normwaarde-kwantitatievewaardeeenheid"
                name="kwantitatievewaardeeenheid"
                data-cy="kwantitatievewaardeeenheid"
                type="text"
              />
              <ValidatedField
                label="Kwantitatievewaardeomvang"
                id="normwaarde-kwantitatievewaardeomvang"
                name="kwantitatievewaardeomvang"
                data-cy="kwantitatievewaardeomvang"
                type="text"
              />
              <ValidatedField
                label="Geldtvoor Locatie"
                id="normwaarde-geldtvoorLocatie"
                data-cy="geldtvoorLocatie"
                type="select"
                multiple
                name="geldtvoorLocaties"
              >
                <option value="" key="0" />
                {locaties
                  ? locaties.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="normwaarde-bevatNorm" name="bevatNorm" data-cy="bevatNorm" label="Bevat Norm" type="select" required>
                <option value="" key="0" />
                {norms
                  ? norms.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>This field is required.</FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/normwaarde" replace color="info">
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

export default NormwaardeUpdate;
