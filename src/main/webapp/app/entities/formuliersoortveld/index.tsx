import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Formuliersoortveld from './formuliersoortveld';
import FormuliersoortveldDetail from './formuliersoortveld-detail';
import FormuliersoortveldUpdate from './formuliersoortveld-update';
import FormuliersoortveldDeleteDialog from './formuliersoortveld-delete-dialog';

const FormuliersoortveldRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Formuliersoortveld />} />
    <Route path="new" element={<FormuliersoortveldUpdate />} />
    <Route path=":id">
      <Route index element={<FormuliersoortveldDetail />} />
      <Route path="edit" element={<FormuliersoortveldUpdate />} />
      <Route path="delete" element={<FormuliersoortveldDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FormuliersoortveldRoutes;
