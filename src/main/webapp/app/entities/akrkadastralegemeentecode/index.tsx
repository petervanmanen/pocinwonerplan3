import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Akrkadastralegemeentecode from './akrkadastralegemeentecode';
import AkrkadastralegemeentecodeDetail from './akrkadastralegemeentecode-detail';
import AkrkadastralegemeentecodeUpdate from './akrkadastralegemeentecode-update';
import AkrkadastralegemeentecodeDeleteDialog from './akrkadastralegemeentecode-delete-dialog';

const AkrkadastralegemeentecodeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Akrkadastralegemeentecode />} />
    <Route path="new" element={<AkrkadastralegemeentecodeUpdate />} />
    <Route path=":id">
      <Route index element={<AkrkadastralegemeentecodeDetail />} />
      <Route path="edit" element={<AkrkadastralegemeentecodeUpdate />} />
      <Route path="delete" element={<AkrkadastralegemeentecodeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AkrkadastralegemeentecodeRoutes;
