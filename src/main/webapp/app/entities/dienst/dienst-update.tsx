import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IZaaktype } from 'app/shared/model/zaaktype.model';
import { getEntities as getZaaktypes } from 'app/entities/zaaktype/zaaktype.reducer';
import { IOnderwerp } from 'app/shared/model/onderwerp.model';
import { getEntities as getOnderwerps } from 'app/entities/onderwerp/onderwerp.reducer';
import { IProduct } from 'app/shared/model/product.model';
import { getEntities as getProducts } from 'app/entities/product/product.reducer';
import { IDienst } from 'app/shared/model/dienst.model';
import { getEntity, updateEntity, createEntity, reset } from './dienst.reducer';

export const DienstUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const zaaktypes = useAppSelector(state => state.zaaktype.entities);
  const onderwerps = useAppSelector(state => state.onderwerp.entities);
  const products = useAppSelector(state => state.product.entities);
  const dienstEntity = useAppSelector(state => state.dienst.entity);
  const loading = useAppSelector(state => state.dienst.loading);
  const updating = useAppSelector(state => state.dienst.updating);
  const updateSuccess = useAppSelector(state => state.dienst.updateSuccess);

  const handleClose = () => {
    navigate('/dienst');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getZaaktypes({}));
    dispatch(getOnderwerps({}));
    dispatch(getProducts({}));
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
      ...dienstEntity,
      ...values,
      startZaaktype: zaaktypes.find(it => it.id.toString() === values.startZaaktype?.toString()),
      heeftOnderwerp: onderwerps.find(it => it.id.toString() === values.heeftOnderwerp?.toString()),
      betreftProduct: products.find(it => it.id.toString() === values.betreftProduct?.toString()),
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
          ...dienstEntity,
          startZaaktype: dienstEntity?.startZaaktype?.id,
          heeftOnderwerp: dienstEntity?.heeftOnderwerp?.id,
          betreftProduct: dienstEntity?.betreftProduct?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="demo3App.dienst.home.createOrEditLabel" data-cy="DienstCreateUpdateHeading">
            Create or edit a Dienst
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="dienst-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField id="dienst-startZaaktype" name="startZaaktype" data-cy="startZaaktype" label="Start Zaaktype" type="select">
                <option value="" key="0" />
                {zaaktypes
                  ? zaaktypes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="dienst-heeftOnderwerp"
                name="heeftOnderwerp"
                data-cy="heeftOnderwerp"
                label="Heeft Onderwerp"
                type="select"
              >
                <option value="" key="0" />
                {onderwerps
                  ? onderwerps.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="dienst-betreftProduct"
                name="betreftProduct"
                data-cy="betreftProduct"
                label="Betreft Product"
                type="select"
              >
                <option value="" key="0" />
                {products
                  ? products.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/dienst" replace color="info">
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

export default DienstUpdate;
